package org.haw.cas.Adapters.rtcadapter.test

import org.haw.cas.web.model.timeline.TimelineComponent
import play.api.libs.json._

object TimelineTest extends App{
  val a = JsArray(JsString("1") :: JsString("2") :: Nil)
  val b = JsArray(JsString("3") :: JsString("4") :: Nil)
  
   println(a +: b +: a +: b +: JsArray())
  
  println(a ++ b)
  
  println(a +: b)
  println(a prepend b)
  
  println(a :+ b)
  println(a append b)
  
  println(TimelineComponent.Converter.toJson(RabbitSenderTimelineTest.testmsg3.getTimelines()))
}