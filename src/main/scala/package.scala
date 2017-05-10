package im.xun

package object vpn {

  val log = org.slf4j.LoggerFactory.getLogger("VPN:")

  object Protocols {
    val Udp = 17
    val Tcp = 6
  }

  lazy val os = System.getProperty("os.name") match {
    case name if name.equals("Mac OS X") =>
      "mac"
    case name if name.equals("Linux") =>
      "lin"
  }
}
