//package im.xun.vpn
//
//import java.nio.ByteBuffer
//
//import Utils._
//import org.scalatest.{Matchers, FlatSpec}
//import tcpstack.Ipv4Package
//
//class UtilsSpec extends FlatSpec with Matchers{
//  val itx = ignore
//  itx should "parse hex string" in {
//    val hex =
//      """001801bf 6adc0025 4bb7afec 08004500
//        |0041a983 40004006 d69ac0a8 00342f8c
//        |ca30c3ef 008f2e80 11f52ea8 4b578018
//        |ffffa6ea 00000101 080a152e ef03002a
//        |2c943538 322e3430 204e4f4f 500d0a""".stripMargin
//
//    val parsedHex = bytesToHexStr(bytesFromHexStr(hex)," ")
//    println(parsedHex)
//  }
//
//  itx should "set & get ByteBuffer" in {
//    val hex =
//      """001801bf 6adc0025 4bb7afec 08004500
//        |0041a983 40004006 d69ac0a8 00342f8c
//        |ca30c3ef 008f2e80 11f52ea8 4b578018
//        |ffffa6ea 00000101 080a152e ef03002a
//        |2c943538 322e3430 204e4f4f 500d0a""".stripMargin
//    val buf = ByteBuffer.wrap(bytesFromHexStr(hex))
//    val p = new Ipv4Package(buf,100)
//    p.get(0,16) shouldBe 0x0018
//    p.get(36,16) shouldBe  0xadc0
//    p.set(36,16, 0x5cB7)
//    p.get(36,16) shouldBe  0x5CB7
//
//    p.get(76,10) shouldBe 0xb
//
//  }
//
//  it should "set ByteBuffer" in {
//    val hex = "ffffffff"
//    val buf = ByteBuffer.wrap(bytesFromHexStr(hex))
//    val p = new Ipv4Package(buf,32)
//    p.get(0,16) shouldBe 0xFFFF
//
//    p.set(1,16,0x1024)
//    p.get(1,16) shouldBe 0x1024
//
//  }
//}
