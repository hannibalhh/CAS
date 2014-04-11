package org.haw.cas.web.model

import play.api.libs.json._

object ErrorComponent {

  def message(s: String) = {
    JsObject(Seq(
      "errormessage" -> JsString(s)))
  }
}