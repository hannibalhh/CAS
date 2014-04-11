package org.haw.cas.web.model.crevasse

import play.api.libs.json.JsObject
import play.api.libs.json.JsNull
import play.api.libs.json.JsArray
import play.api.libs.json.JsString
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.CrevassesMessage._
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.AkkaMessage.AkkaMessageBuilder
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateCrevassesMessage.UpdateCrevasses
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
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateCrevassesMessage.UpdateCrevasses
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.GeoCoordinatesMessage.GeoCoordinates
import org.haw.cas.Adapters.rtcadapter.Topics

object CrevasseComponent extends App {

  lazy implicit val timeout = Timeout(100 second)
  lazy val system = Environment.system
  lazy val crevasseactor: ActorRef = system.actorOf(Props[CrevassesBufferActor], "crevassesbuffer")

  def apply() = Await.result(crevasse, 100 seconds).asInstanceOf[JsObject]

  def crevasse = {
    (crevasseactor ? UpdateCrevasses.newBuilder.build).map {
      case n: Crevasses => {
        Converter.toJson(n)
      }
    }
  }

  object Converter {
    def toJson(n: Crevasse, id: String) = {
      JsObject(
        Seq(
          "type" -> JsString("Crevasse"),
          "id" -> JsString(id),
          "geo" -> JsObject(Seq("coordinates" -> GeoCoordinatesComponent.convert(n.getGeo()))),
          "author" -> JsString(n.getAuthor()),
          "message" -> JsString(n.getMessage()),
          "provenance" -> JsString(n.getProvenance()+""),
          "creationTime" -> TimeComponent.convert(TimeComponent.toTime(n.getCreationTime()))))
    }

    def toJson(crevasses: Crevasses): JsObject = {
      import scala.collection.JavaConversions._
      JsObject(Seq("crevasses" ->
        JsArray(crevasses.getCrevasseList().view.zipWithIndex.map((x => toJson(x._1, x._2.toString))))))
    }
  }

  object Messages {
    lazy val updateCrevasses = UpdateCrevasses.newBuilder.build
    lazy val metaUpdateCrevasses =
      AkkaMessageBuilder.newBuilder().setSender(Topics.crevasses.toString).
        setUpdateCrevasses(UpdateCrevasses.newBuilder).
        setType(MessageType.UpdateCrevassesMessage).build()
  }

}






