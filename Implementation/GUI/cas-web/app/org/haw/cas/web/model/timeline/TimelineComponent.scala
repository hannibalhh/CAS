package org.haw.cas.web.model.timeline

import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.AkkaMessage.AkkaMessageBuilder
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.AkkaMessage.AkkaMessageBuilder.MessageType
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.Timeline
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.Timelines
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.UpdateTimelines
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
import play.api.libs.json.JsObject
import play.api.libs.json.JsString
import play.api.libs.json.JsArray
import org.haw.cas.Adapters.rtcadapter.Topics
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.TimelineRequest
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.TimelineType
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.PositionSetting
import org.haw.cas.web.model.PointComponent
import org.haw.cas.web.model.HashingComponent
import play.api.libs.json.JsNull
import org.haw.cas.Adapters.logging.LowLog

object TimelineComponent extends LowLog {

  lazy implicit val timeout = Timeout(100 second)
  lazy val system = Environment.system
  lazy val timelineactor: ActorRef = system.actorOf(Props[TimelineBufferActor], "timelinebuffer")

  def apply(t: List[TimelineRequest], from: Long, to: Long) = Await.result(timeline(t, from, to), 100 seconds).asInstanceOf[JsObject]

  def timeline(t: List[TimelineRequest], from: Long, to: Long) = {
    val hash = HashingComponent.hash(t.toString + from + to)
    debug("new timeline request id: " + hash)
    (timelineactor ? Messages.updateTimelines(hash, t, from, to)).map {
      case n: Timelines => {
        Converter.toJson(n)
      }
    }
  }

  object Converter {

    def timelinetype(t: TimelineType) = t.toString().substring(1, t.toString().size)

    def toHead(n: Timeline) =
      JsString(timelinetype(n.getType))

    def toY(n: Timeline, i: Int) = {
      import scala.collection.JavaConversions._
      PointComponent.Converter.toY(n.getDataPoints(i))
    }
    def toX(n: Timeline, i: Int) = {
      import scala.collection.JavaConversions._
      PointComponent.Converter.toX(n.getDataPoints(i))
    }

    def rows(timelines: List[Timeline], num: Int, i: Int): JsArray = {
      if (i >= num)
        JsArray(toX(timelines(0), i) :: timelines.map(x => toY(x, i))) +: JsArray()
      else 
        JsArray(toX(timelines(0), i) :: timelines.map(x => toY(x, i))) +: rows(timelines, num, i + 1)
    }
    
    def validate(timelines: List[Timeline]):Boolean = {
      val count = timelines.apply(0).getDataPointsCount
      for (t <- timelines)
        if (t.getDataPointsCount() != count)
          return false
      return true
    }

    def toJson(timelines: Timelines): JsObject = {
      import scala.collection.JavaConversions._
      if (timelines.getTimelinesList().isEmpty() ){
        println("timeslines not valid, Timelinelist shouldnt be empty")
        JsObject(Seq("timelines" -> JsArray()))
      }
      else if (!validate(timelines.getTimelinesList.toList)){
        println("timeslines not valid, Timeline length of all timelines have to be equal")
        JsObject(Seq("timelines" -> JsArray()))
      }
      else {        
        val head = JsArray(JsString("Date") +: timelines.getTimelinesList.map((x => toHead(x))))
        val r = rows(timelines.getTimelinesList.toList, timelines.getTimelinesList.get(0).getDataPointsCount() - 1, 0)
        JsObject(Seq("timelines" -> (head +: r)))
      }
    }
  }

  object Messages {
    def meta(n: UpdateTimelines) = {
      AkkaMessageBuilder.newBuilder().setSender(Topics.timelines.toString)
        .setUpdateTimelines(n)
        .setType(MessageType.UpdateTimelinesMessage).build()
    }

    def config(t: TimelineType, p: PositionSetting) = {
      TimelineRequest.newBuilder.setType(t).setPositionSetting(p).build
    }

    def updateTimelines(s: String, t: List[TimelineRequest], from: Long, to: Long) = {
      var builder = UpdateTimelines.newBuilder.setRequestId(s).setFrom(from).setTo(to)
      for (item <- t)
        builder = builder.addRequestList(item)
      builder.build
    }

  }

}