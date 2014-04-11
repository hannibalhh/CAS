package org.haw.cas.web.model.posts

import play.api.libs.json.JsObject
import play.api.libs.json.JsNull
import play.api.libs.json.JsArray
import play.api.libs.json.JsString
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.PostsMessage._
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

object PostsComponent extends App {

  lazy implicit val timeout = Timeout(100 second)
  lazy val system = Environment.system
  lazy val postsactor: ActorRef = system.actorOf(Props[PostsBufferActor], "postsbuffer")

  def apply() = Await.result(posts, 100 seconds).asInstanceOf[JsObject]

  def posts = {
    (postsactor ? UpdatePosts.newBuilder.build).map {
      case n: Posts => {
        Converter.toJson(n)
      }
    }
  }

  object Converter {
    def toJson(n: Post, id: String) = {
      JsObject(
        Seq(
          "type" -> JsString("Post"),
          "id" -> JsString(id),
          "author" -> JsString(n.getAuthor()),
          "message" -> JsString(n.getMessage()),
          "provenance" -> JsString(n.getProvenance()+""),
          "creationTime" -> TimeComponent.convert(TimeComponent.toTime(n.getCreationTime()))))
    }

    def toJson(posts: Posts): JsObject = {
      import scala.collection.JavaConversions._
      JsObject(Seq("Posts" ->
        JsArray(posts.getPostsList().view.zipWithIndex.map((x => toJson(x._1, x._2.toString))))))
    }
  }

  object Messages {
    lazy val updatePosts = UpdatePosts.newBuilder.build
    lazy val metaUpdatePosts =
      AkkaMessageBuilder.newBuilder().setSender(Topics.posts.toString).
        setUpdatePosts(UpdatePosts.newBuilder).
        setType(MessageType.UpdatePostsMessage).build()
  }

}






