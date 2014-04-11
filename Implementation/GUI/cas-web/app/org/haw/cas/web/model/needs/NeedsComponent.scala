package org.haw.cas.web.model.needs

import play.api.libs.json.JsObject
import play.api.libs.json.JsNull
import play.api.libs.json.JsArray
import play.api.libs.json.JsString
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage._
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.AkkaMessage.AkkaMessageBuilder
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateNeedsMessage.UpdateNeeds
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
import org.haw.cas.Adapters.rtcadapter.Topics

object NeedsComponent extends App {

  lazy implicit val timeout = Timeout(100 second)
  lazy val system = Environment.system
  lazy val needsactor: ActorRef = system.actorOf(Props[NeedsBufferActor], "needsbuffer")
  
  def apply() = Await.result(needs, 100 seconds).asInstanceOf[JsObject]

  def needs = {
    (needsactor ? Messages.updateNeeds).map {
      case n: Needs => {
        Converter.toJson(n)
      }
    }
  }

  object Converter {

    object Types extends Enumeration {
      type Type = Value
      val Helper, Accomodation, Medicament, Food, Drink = Value
    }

    def toJson(n: Helper,id:String) = {
      JsObject(
        Seq(
          "id" -> JsString(id),
          "type" -> JsString(Types.Helper.toString()),
          "geo" -> JsObject(Seq("coordinates" -> GeoCoordinatesComponent.convert(n.getGeo()))),
          "author" -> JsString(n.getAuthor()),
          "pictureUrl" -> JsString("http://a0.twimg.com/profile_images/378800000401717541/641b261c4602b230f309b14dfbb60694_normal.png"),
          "message" -> JsString(n.getMessage()),
          "provenance" -> JsString(n.getProvenance()+""),
          "creationTime" -> TimeComponent.convert(TimeComponent.toTime(n.getCreationTime()))))
    }

    def toJson(n: Food,id:String) = {
      JsObject(
        Seq(
          "id" -> JsString(id),
          "type" -> JsString(Types.Food.toString()),
          "geo" -> JsObject(Seq("coordinates" -> GeoCoordinatesComponent.convert(n.getGeo()))),
          "author" -> JsString(n.getAuthor()),
          "pictureUrl" -> JsString("http://a0.twimg.com/profile_images/378800000401717541/641b261c4602b230f309b14dfbb60694_normal.png"),
          "message" -> JsString(n.getMessage()),
          "provenance" -> JsString(n.getProvenance()+""),
          "creationTime" -> TimeComponent.convert(TimeComponent.toTime(n.getCreationTime()))))
    }

    def toJson(n: Drink,id:String) = {
      JsObject(
        Seq(
          "id" -> JsString(id),
          "type" -> JsString(Types.Food.toString()),
          "geo" -> JsObject(Seq("coordinates" -> GeoCoordinatesComponent.convert(n.getGeo()))),
          "author" -> JsString(n.getAuthor()),
          "pictureUrl" -> JsString("http://a0.twimg.com/profile_images/378800000401717541/641b261c4602b230f309b14dfbb60694_normal.png"),
          "message" -> JsString(n.getMessage()),
          "provenance" -> JsString(n.getProvenance()+""),
          "creationTime" -> TimeComponent.convert(TimeComponent.toTime(n.getCreationTime()))))
    }

    def toJson(n: Accomodation,id:String) = {
      JsObject(
        Seq(
          "id" -> JsString(id),
          "type" -> JsString(Types.Accomodation.toString()),
          "geo" -> JsObject(Seq("coordinates" -> GeoCoordinatesComponent.convert(n.getGeo()))),
          "author" -> JsString(n.getAuthor()),
          "pictureUrl" -> JsString("http://a0.twimg.com/profile_images/378800000401717541/641b261c4602b230f309b14dfbb60694_normal.png"),
          "message" -> JsString(n.getMessage()),
          "provenance" -> JsString(n.getProvenance()+""),
          "creationTime" -> TimeComponent.convert(TimeComponent.toTime(n.getCreationTime()))))
    }

    def toJson(n: Medicament,id:String) = {
      JsObject(
        Seq(
          "id" -> JsString(id),
          "type" -> JsString(Types.Medicament.toString()),
          "geo" -> JsObject(Seq("coordinates" -> GeoCoordinatesComponent.convert(n.getGeo()))),
          "author" -> JsString(n.getAuthor()),
          "pictureUrl" -> JsString("http://a0.twimg.com/profile_images/378800000401717541/641b261c4602b230f309b14dfbb60694_normal.png"),
          "message" -> JsString(n.getMessage()),
          "provenance" -> JsString(n.getProvenance()+""),
          "creationTime" -> TimeComponent.convert(TimeComponent.toTime(n.getCreationTime()))))
    }

    def toJson(needs: Needs):JsObject = {
      import scala.collection.JavaConversions._
      JsObject(Seq("needs" -> {
        JsArray(needs.getHelperList.view.zipWithIndex.map((x => toJson(x._1,x._2.toString)))) ++
          JsArray(needs.getAccomodationList.view.zipWithIndex.map((x => toJson(x._1,x._2.toString)))) ++
          JsArray(needs.getDrinkList.view.zipWithIndex.map((x => toJson(x._1,x._2.toString)))) ++
          JsArray(needs.getFoodList.view.zipWithIndex.map((x => toJson(x._1,x._2.toString))))++
          JsArray(needs.getMedicamentList.view.zipWithIndex.map((x => toJson(x._1,x._2.toString))))
      }))
    }

    

  }
  
  object Messages {
    lazy val updateNeeds = UpdateNeeds.newBuilder.build
    lazy val metaUpdateNeeds =
      AkkaMessageBuilder.newBuilder().setSender(Topics.needs.toString).
        setUpdateNeeds(UpdateNeeds.newBuilder()).
        setType(MessageType.UpdateNeedsMessage).build()
  }

}






