package org.haw.cas.web.model

object HashingComponent {

  val md = java.security.MessageDigest.getInstance("SHA-1")
  def hash(s: String):String = byteArrayToHexString(md.digest(s.getBytes))

  def byteArrayToHexString(b: Array[Byte]) = {
    var result = ""
    for (i <- 0 until b.length) {
      result +=
        Integer.toString((b(i) & 0xff) + 0x100, 16).substring(1);
    }
    result
  }
}

object HashingComponentTest extends App {
  println(HashingComponent.hash("foo"))
}