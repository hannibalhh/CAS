package org.haw.cas.web.model

import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.Point
import play.api.libs.json.JsObject
import play.api.libs.json.JsString
import play.api.libs.json.JsArray
import play.api.libs.json.JsNumber

object PointComponent {

  object Converter {
    def toJson(p: Point) = { 
      JsArray(
        Seq(TimeComponent.convert(TimeComponent.toTime(p.getX)),
          JsNumber(p.getY)))
    }
    
    def toY(p: Point) = JsNumber(p.getY)
    def toX(p: Point) = TimeComponent.convert(TimeComponent.toTime(p.getX))
    
    def head(xname: String, yname: String) = {
      JsArray(
        Seq(JsString(xname),
          JsString(yname)))
    }
  }

}