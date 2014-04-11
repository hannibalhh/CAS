package org.haw.cas.web.model.timeline

import play.api.libs.iteratee._
import play.api.libs.json._
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits._
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.util.Timeout
import akka.pattern.ask
import scala.concurrent.duration._
import akka.event.slf4j.Logger
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.UpdateTimelines
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.Timeline
import org.haw.cas.web.model.timeline.TimelineComponent.Messages._
import org.haw.cas.web.model.timeline.TimelineComponent._
import akka.event.Logging
import org.haw.cas.Adapters.rtcadapter.Rabbit
import org.haw.cas.Adapters.rtcadapter.Subscribe
import org.haw.cas.Adapters.rtcadapter.Topics
import org.haw.cas.web.model.socket.Connect
import org.haw.cas.web.model.socket.Trigger
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.Timelines
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.TimelineType
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.PositionSetting
import org.haw.cas.Adapters.logging.Log

class TimelineSocketActor extends Actor with Log{

  val loggerIteratee = Iteratee.foreach[JsValue](event => Logger("robot").info(event.toString))
  val (enumerator, channel) = Concurrent.broadcast[JsValue]
  
  override def preStart = {
     log.debug("started")
     Rabbit.delegator ! Subscribe(Topics.timelines)
     Rabbit.delegator ! Subscribe(Topics.broadcast)
  }

  def receive = {
    case Trigger => timelineactor ! updateTimelines("#01",List(config(TimelineType.TAccomodation, PositionSetting.Both)),0, 1)
    case x: UpdateTimelines => {
      enumerator |>> loggerIteratee
      sender ! Connect(enumerator)
      log.debug("connected")
    }
    case x:Timelines => {     
      log.debug("push")
      channel.push(Converter.toJson(x))
    }
    case _ =>
  }

  
}