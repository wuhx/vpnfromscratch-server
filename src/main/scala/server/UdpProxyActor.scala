package im.xun.vpn

import java.net.InetSocketAddress
import akka.actor.{ActorRef, Actor}
import akka.io.{UdpConnected, IO}
import ColorString._

class UdpProxyActor(target: InetSocketAddress) extends Actor {
  import context.system

  log.info(s"UdpProxyActor: Connect to $target".blue)
  IO(UdpConnected) ! UdpConnected.Connect(self, target)

  def receive = {
    case UdpConnected.Connected =>
      log.info("Udp proxy target connected!".blue)
      context.parent ! ProxyConnected
      context.become(ready(sender()))

    case msg =>
      log.info(s"UdpProxyActor: Target not connected! $msg".blue)
  }

  def ready(connection: ActorRef): Receive = {
    case ProxySend(data) =>
      log.info(s"UdpProxyActor: Proxy send ${data.length} bytes".blue)
      connection ! UdpConnected.Send(data)

    case UdpConnected.Received(data) =>
      log.info(s"receive from target ${data.length} bytes!".blue)
      context.parent ! ProxyRecv(data)

    case UdpConnected.Disconnect =>
      log.info(s"UdpProxyActor: Disconnect".blue)
      connection ! UdpConnected.Disconnect

    case UdpConnected.Disconnected =>
      log.info(s"UdpProxyActor: Disconnected".blue)
      context.stop(self)

    case msg =>
      log.info(s"TcpProxyActor: unexpected message: $msg".blue)
  }
}
