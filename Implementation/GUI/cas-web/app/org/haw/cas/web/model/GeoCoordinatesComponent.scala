package org.haw.cas.web.model

import play.api.libs.json.JsArray
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.GeoCoordinatesMessage
import play.api.libs.json.JsString

object GeoCoordinatesComponent {

  def convert(geo:GeoCoordinatesMessage.GeoCoordinates) = JsArray(Seq(
      JsString(geo.getLatitude.toString), JsString(geo.getLongitude.toString)))
  
}