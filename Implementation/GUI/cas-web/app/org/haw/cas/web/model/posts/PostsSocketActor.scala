package org.haw.cas.web.model.posts

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
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.PostsMessage.Posts
import org.haw.cas.web.model.posts.PostsComponent._
import akka.event.Logging
import org.haw.cas.Adapters.rtcadapter.Rabbit
import org.haw.cas.Adapters.rtcadapter.Subscribe
import org.haw.cas.Adapters.rtcadapter.Topics
import org.haw.cas.web.model.socket.Connect
import org.haw.cas.web.model.socket.Trigger
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.PostsMessage.UpdatePosts
import org.haw.cas.Adapters.logging.Log

class PostsSocketActor extends Actor with Log{
  val loggerIteratee = Iteratee.foreach[JsValue](event => Logger("robot").info(event.toString))
  val (enumerator, channel) = Concurrent.broadcast[JsValue]

  override def preStart = {
    log.debug("started")
    Rabbit.delegator ! Subscribe(Topics.posts)
    Rabbit.delegator ! Subscribe(Topics.broadcast)
  }

  def receive = {
    case Trigger => postsactor ! Messages.updatePosts
    case x: UpdatePosts => {
      enumerator |>> loggerIteratee
      sender ! Connect(enumerator)
      log.debug("connected")
    }
    case x: Posts => {
      log.debug("push")
      channel.push(Converter.toJson(x))
    }
    case _ =>
  }
}
