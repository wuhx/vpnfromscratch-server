package im.xun.vpn

///https://github.com/ktoso/scala-rainbow/blob/master/src%2Fmain%2Fscala%2Fpl%2Fproject13%2Fscala%2Frainbow%2FRainbow.scala
object ColorString {

  implicit def colorString(s: String) = new RainbowString(s)
  implicit def hexInt(n: Int) = new HexNumber[Int](n)
  implicit def hexNumber(n: Long) = new HexNumber[Long](n)
  class HexNumber[T](n: T) {
    def h = "0x%X".format(n)
    //def b= n.toBinaryString
  }


  class RainbowString(s: String) {
    import Console._

    /** Colorize the given string foreground to ANSI black */
    def black = BLACK + s + RESET
    /** Colorize the given string foreground to ANSI red */
    def red = RED + s + RESET
    /** Colorize the given string foreground to ANSI red */
    def green = GREEN + s + RESET
    /** Colorize the given string foreground to ANSI red */
    def yellow = YELLOW + s + RESET
    /** Colorize the given string foreground to ANSI red */
    def blue = BLUE + s + RESET
    /** Colorize the given string foreground to ANSI red */
    def magenta = MAGENTA + s + RESET
    /** Colorize the given string foreground to ANSI red */
    def cyan = CYAN + s + RESET
    /** Colorize the given string foreground to ANSI red */
    def white = WHITE + s + RESET

    /** Colorize the given string background to ANSI red */
    def onBlack = BLACK_B + s + RESET
    /** Colorize the given string background to ANSI red */
    def onRed = RED_B+ s + RESET
    /** Colorize the given string background to ANSI red */
    def onGreen = GREEN_B+ s + RESET
    /** Colorize the given string background to ANSI red */
    def onYellow = YELLOW_B + s + RESET
    /** Colorize the given string background to ANSI red */
    def onBlue = BLUE_B+ s + RESET
    /** Colorize the given string background to ANSI red */
    def onMagenta = MAGENTA_B + s + RESET
    /** Colorize the given string background to ANSI red */
    def onCyan = CYAN_B+ s + RESET
    /** Colorize the given string background to ANSI red */
    def onWhite = WHITE_B+ s + RESET

    /** Make the given string bold */
    def bold = BOLD + s + RESET
    /** Underline the given string */
    def underlined = UNDERLINED + s + RESET
    /** Make the given string blink (some terminals may turn this off) */
    def blink = BLINK + s + RESET
    /** Reverse the ANSI colors of the given string */
    def reversed = REVERSED + s + RESET
    /** Make the given string invisible using ANSI color codes */
    def invisible = INVISIBLE + s + RESET
  }
}
