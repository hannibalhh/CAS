package org.haw.cas.web.model.crevasse

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
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateCrevassesMessage.UpdateCrevasses
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.CrevassesMessage.Crevasses
import org.haw.cas.web.model.crevasse.CrevasseComponent._
import akka.event.Logging
import org.haw.cas.Adapters.rtcadapter.Rabbit
import org.haw.cas.Adapters.rtcadapter.Subscribe
import org.haw.cas.Adapters.rtcadapter.Topics
import org.haw.cas.web.model.socket.Connect
import org.haw.cas.web.model.socket.Trigger
import org.haw.cas.Adapters.logging.Log

class CrevassesSocketActor extends Actor with Log{
  val loggerIteratee = Iteratee.foreach[JsValue](event => Logger("robot").info(event.toString))
  val (enumerator, channel) = Concurrent.broadcast[JsValue]

  override def preStart = {
    log.debug("started")
    Rabbit.delegator ! Subscribe(Topics.crevasses)
    Rabbit.delegator ! Subscribe(Topics.broadcast)
  }

  def receive = {
    case Trigger => crevasseactor ! Messages.updateCrevasses
    case x: UpdateCrevasses => {
      enumerator |>> loggerIteratee
      sender ! Connect(enumerator)
      log.debug("connected")
    }
    case x: Crevasses => {
      log.debug("push")
      channel.push(Converter.toJson(x))
    }
    case _ =>
  }
}
