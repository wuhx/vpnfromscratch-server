package im.xun.vpn

import akka.actor.{Props, ActorSystem}

object ServerMain extends App {
  val system = ActorSystem()
  val vpnActor = system.actorOf(Props[VpnServerActor],"vpn")
}
