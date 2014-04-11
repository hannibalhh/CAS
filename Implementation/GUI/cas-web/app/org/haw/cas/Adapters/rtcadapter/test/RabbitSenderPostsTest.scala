package org.haw.cas.Adapters.rtcadapter.test

import akka.actor.ActorSystem
import akka.actor.Props
import org.haw.cas.Adapters.rtcadapter.Consumer
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.GeoCoordinatesMessage.GeoCoordinates
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage.Accomodation
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage.Helper
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage.Needs
import akka.actor.Actor
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.AkkaMessage.AkkaMessageBuilder.MessageType
import org.haw.cas.Adapters.rtcadapter.Sender
import com.google.protobuf.Message
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.AkkaMessage.AkkaMessageBuilder
import akka.event.Logging
import akka.actor.ActorRef
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.PostsMessage.Post
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.PostsMessage.Posts

object RabbitSenderPostsTest {

  val foo = "posts"

  def test(sender: ActorRef) {
    sender ! testmsg
  }

  def testmsg: AkkaMessageBuilder = {
    import java.util.Date
    import play.api.libs.json.JsObject
    import com.googlecode.protobuf.format.JsonFormat
    import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage._
    import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.GeoCoordinatesMessage.GeoCoordinates
    import org.haw.cas.web.model.needs.NeedsComponent
    import play.api.libs.json.Json

    val p1 = Post.newBuilder.setAuthor("ich").setCreationTime(System.currentTimeMillis()).setMessage("meine nachricht").setProvenance("foo").setPictureUrl("bla").build()
    val p2 = Post.newBuilder.setAuthor("ich").setCreationTime(System.currentTimeMillis()).setMessage("meine nachricht").setProvenance("foo").setPictureUrl("bla").build()

    val p = Posts.newBuilder.addPosts(p1).addPosts(p2).build()
    //val needs1 = Needs.newBuilder.addAccomodation(acco2).build()
    AkkaMessageBuilder.newBuilder().setType(MessageType.PostsMessage).setSender(foo).setPosts(p).build()
  }

}

object StartRabbitSenderPostsTest extends App {

  val system = ActorSystem("myrabbit")
  val sender = system.actorOf(Props[TestSender], "send")

  //  for (a <- 1 to 10) {
  RabbitSenderPostsTest.test(sender)
  //  }

  // Nach 1minute automatisches beenden
  Thread.sleep(60000)
  System.exit(0)

}

