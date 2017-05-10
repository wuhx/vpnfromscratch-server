package im.xun.vpn

import java.nio.{ByteOrder, ByteBuffer}

case class TunConfig(target: String, port: Int, protocol: Int)

object TunConfig {
  val magic = 0x5151ABCD
  val buf = ByteBuffer.allocate(50)
  buf.order(ByteOrder.BIG_ENDIAN)

  def toByteBuffer(conf: TunConfig): ByteBuffer = {
    buf.clear()
    buf.putInt(magic)

    val target = conf.target.getBytes("utf8")
    buf.putInt(target.length)
    buf.put(target)

    buf.putInt(conf.port)
    buf.putInt(conf.protocol)

    buf.flip()
    buf
  }


  def fromByteBuffer(data: ByteBuffer):(Option[TunConfig], ByteBuffer) = {
    val buf = data.duplicate()
    if( buf.getInt == magic) {
      val len = buf.getInt
      val target = new String(buf.array(),buf.position(),len)

      buf.position(buf.position + len)
      val port = buf.getInt
      val protocol = buf.getInt

      val remain = buf.slice()
      (Some(TunConfig(target,port,protocol)), remain)
    } else {
      (None, buf)
    }
  }
}
