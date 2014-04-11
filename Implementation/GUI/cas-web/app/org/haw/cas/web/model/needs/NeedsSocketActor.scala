package org.haw.cas.web.model.needs

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
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateNeedsMessage.UpdateNeeds
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage.Needs
import org.haw.cas.web.model.needs.NeedsComponent._
import akka.event.Logging
import org.haw.cas.Adapters.rtcadapter._
import org.haw.cas.web.model.socket._
import org.haw.cas.Adapters.logging.Log

class NeedsSocketActor extends Actor with Log{
  val loggerIteratee = Iteratee.foreach[JsValue](event => Logger("robot").info(event.toString))
  val (enumerator, channel) = Concurrent.broadcast[JsValue]

  override def preStart = {
    log.debug("started")
     Rabbit.delegator ! Subscribe(Topics.needs)
     Rabbit.delegator ! Subscribe(Topics.broadcast)
  }

  def receive = {
    case Trigger => needsactor ! Messages.updateNeeds
    case x: UpdateNeeds => {  
      enumerator |>> loggerIteratee
      sender ! Connect(enumerator)
      log.debug("connected")
    }
    case x:Needs => {     
      log.debug("push")
      channel.push(Converter.toJson(x))
    }
    case _ =>
  }
}
