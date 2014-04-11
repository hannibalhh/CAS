package org.haw.cas.web.model.userpositions

import play.api.libs.json.JsObject
import play.api.libs.json.JsNull
import play.api.libs.json.JsArray
import play.api.libs.json.JsString
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.AkkaMessage.AkkaMessageBuilder
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.AkkaMessage.AkkaMessageBuilder.MessageType
import org.haw.cas.web.model.GeoCoordinatesComponent
import org.haw.cas.web.model.TimeComponent
import akka.actor.ActorSystem
import akka.actor.ActorRef
import akka.util.Timeout
import akka.actor.Props
import akka.pattern.ask
import scala.concurrent.duration._
import scala.concurrent.Await
import scala.collection.JavaConversions.asScalaBuffer
import play.api.libs.concurrent.Execution.Implicits._
import org.haw.cas.web.model.Environment
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.GeoCoordinatesMessage.GeoCoordinates
import org.haw.cas.Adapters.rtcadapter.Topics
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateUserPositionsMessage.UpdateUserPositions
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UserPositionsMessage.UserPositions
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UserPositionsMessage.UserPosition

object UserPositionsComponent extends App {

  lazy implicit val timeout = Timeout(100 second)
  lazy val system = Environment.system
  lazy val userpositionsactor: ActorRef = system.actorOf(Props[UserPositionsBufferActor], "userpositionbuffer")

  def apply(u:UpdateUserPositions) = Await.result(userposition(u), 100 seconds).asInstanceOf[JsObject]

  def userposition(u:UpdateUserPositions) = {
    (userpositionsactor ? u).map {
      case n: UserPositions => {
        Converter.toJson(n)
      }
    }
  }

  object Converter {
    def toJson(n: UserPosition, id: String) = {
      JsObject(
        Seq(
          "type" -> JsString("Userpositions"),
          "id" -> JsString(id),
          "geo" -> JsObject(Seq("coordinates" -> GeoCoordinatesComponent.convert(n.getCenter))),
          "author" -> JsString(n.getUser()),
          "message" -> JsString(n.getMessage),
          "creationTime" -> TimeComponent.convert(TimeComponent.toTime(n.getCreationTime())),
          "provenance" -> JsString(n.getProvenance+""),
          "radius" -> JsString(n.getRadius+""),
          "certainty" -> JsString(n.getCertainty+"")))
    }

    def toJson(userpositions: UserPositions): JsObject = {
      import scala.collection.JavaConversions._
      JsObject(Seq(
         "requestid" -> JsString(userpositions.getRequestId+""),
         "userpositions" ->
        	JsArray(userpositions.getUserPositionsList.view.zipWithIndex.map((x => toJson(x._1, x._2.toString))))))
    }
  }

  object Messages {
    lazy val updateUserPositions = UpdateUserPositions.newBuilder.setCenter(GeoCoordinates.newBuilder.setLatitude(51f).setLongitude(9f).build).setOldest(98).setRadius(10).setRequestId("#00").build
    def metaUpdateUserPositions(u:UpdateUserPositions) =
      AkkaMessageBuilder.newBuilder().setSender(Topics.userPositions.toString).
        setUpdateUserPositions(u).
        setType(MessageType.UpdateUserPositionsMessage).build()
  }

}






