package org.haw.cas.web.model.socket

import play.api.libs.iteratee.Enumerator
import play.api.libs.json.JsValue

case class CannotConnect(error: String)
case class Connect(out: Enumerator[JsValue]) 
case object Trigger