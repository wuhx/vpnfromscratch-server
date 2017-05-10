package im.xun.vpn

import java.net.InetSocketAddress

import akka.actor.{ActorRef, Actor}
import akka.io.{IO, Tcp}
import akka.util.{Timeout, ByteString}

import akka.pattern.ask
import scala.concurrent.Await
import scala.concurrent.duration._

case object ProxyConnected
case class ProxySend(data: ByteString)
case class ProxyRecv(data: ByteString)

class TcpProxyActor(target: InetSocketAddress) extends Actor {
  import Tcp._
  import context.system
  log.info(s"TcpProxyActor: Connect to $target")

  IO(Tcp) ! Connect(target)

  var connection: ActorRef = _

  var status = false

  def receive = {

    case fail @ CommandFailed(_: Connect) =>
      log.error("connect failed")
      context.parent ! fail
      context stop self

    case Connected(_,_) =>
      log.info("Tcp proxy target connected!")
      status = true
      connection = sender()
      context.parent ! ProxyConnected
      connection ! Register(self)

    case Received(data) =>
      log.info(s"receive from target ${data.length} bytes!")
      context.parent ! ProxyRecv(data)

    case ProxySend(data) =>
      log.info(s"TcpProxyActor: Proxy send ${data.length} bytes")
      while(!status){
        Thread.sleep(10)
      }
      connection ! Tcp.Write(data)

    case msg =>
      log.info(s"TcpProxyActor: unexpected message: $msg")
  }
}
