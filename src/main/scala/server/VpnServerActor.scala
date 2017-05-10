package im.xun.vpn

import java.net.InetSocketAddress

import akka.actor.{Props, Actor}
import akka.io.Tcp.Connect
import akka.io.{IO, Tcp}

class VpnServerActor extends Actor {
  import Tcp._
  import context.system

  IO(Tcp) ! Bind(self, new InetSocketAddress("0.0.0.0", 8080))

  def receive = {
    case b @ Bound(localAddress) =>
      log.info(s"Bound to $localAddress")

    case CommandFailed(e: Bind) =>
      log.error(s"Bind Failed! $e")
      context stop self

    case c @ Connected(remote, local) =>
      val handler = context.actorOf(Props[TcpHandlerActor])
      val connection = sender()
      connection ! Register(handler)

    case msg =>
      log.error("Unexpected message! "+msg)
  }
}
