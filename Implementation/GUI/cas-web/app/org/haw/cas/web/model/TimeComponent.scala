package org.haw.cas.web.model

import java.sql.Timestamp
import play.api.libs.json.JsString
import java.util.Date
import java.text.SimpleDateFormat

object TimeComponent extends App{

  implicit def toTime(timestamp:Long) = new Timestamp(timestamp)
  
  def convert(d:Date) = JsString(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(d))
  def convertSmall(d:Date) = JsString(new SimpleDateFormat("dd.MM.yyyy").format(d))

  
}