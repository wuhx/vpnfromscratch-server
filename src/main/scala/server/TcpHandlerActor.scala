package im.xun.vpn


import java.nio.ByteBuffer

import akka.actor.{Stash, Props, ActorRef, Actor}
import akka.io.Tcp
import akka.io.Tcp.{Received, PeerClosed}
import akka.util.{ByteString, Timeout}
import akka.pattern.ask
import scala.concurrent.duration._
import scala.concurrent.Await

import java.net.InetSocketAddress


class TcpHandlerActor extends Actor with Stash {
  import Tcp._

  var target: Option[ActorRef] = None
  var connection: Option[ActorRef] = None

  def createProxyActor(data: ByteBuffer) = {
    try {
      val (t,remain) = TunConfig.fromByteBuffer(data)
      if(t.nonEmpty) {
        val conf = t.get
        val addr =  new InetSocketAddress(conf.target,conf.port)
        val proxyActor = if(conf.protocol == Protocols.Tcp) {
          context.actorOf(Props(classOf[TcpProxyActor], addr))
        } else {
          context.actorOf(Props(classOf[UdpProxyActor], addr))
        }

        target = Some(proxyActor)
        if(remain.limit() != 0) {
          log.info("More data")
          self ! Received(ByteString(remain))
        }
      } else {
        log.error("Fail to config Tun")
      }
    } catch {
      case e: Throwable =>
        log.error("Fail to parse target!" +e)
    }
  }

  def receive = {
    case Received(data) =>
      log.info(s"receive request ${data.length} bytes")
      //dumpBytes(data)
      if(target.isEmpty) {
        connection = Some(sender)
        //echo whatever
//        connection.get ! Tcp.Write(data)

        createProxyActor(data.toByteBuffer)
        context.become({
          case ProxyConnected =>
            context.unbecome()
            unstashAll()
          case msg =>
            stash()
        }, discardOld = false)

      } else {
        target.get ! ProxySend(data)
      }

    case ProxyRecv(data) =>
      log.info(s"Proxy recv")
      connection.get ! Tcp.Write(data)

    case PeerClosed     =>
      log.info("Peer closed")
      context stop self

    case msg =>
      log.error("Unexpected Message: " + msg)
  }
}
