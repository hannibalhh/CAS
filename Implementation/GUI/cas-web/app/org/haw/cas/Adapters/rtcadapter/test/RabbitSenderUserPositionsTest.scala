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
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.CrevassesMessage.Crevasse
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.CrevassesMessage.Crevasses
import org.haw.cas.web.model.timeline.TimelineComponent
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UserPositionsMessage.UserPosition
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UserPositionsMessage.UserPositions
import akka.actor.ActorRef
object RabbitSenderUserPositionsTest {

  val foo = "userPositions"

  // http://localhost:9000/userpositions?la=51&lo=9&oldest=0&radius=0

  def test(sender: ActorRef) {
    sender ! testmsg
    //sender ! testmsg2
  }

  def testmsg = {
    import java.util.Date
    import play.api.libs.json.JsObject
    import com.googlecode.protobuf.format.JsonFormat
    import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage._
    import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.GeoCoordinatesMessage.GeoCoordinates
    import org.haw.cas.web.model.needs.NeedsComponent
    import play.api.libs.json.Json
    val geo1 = GeoCoordinates.newBuilder().setLongitude(53.629982f).setLatitude(10.087782f).build()
    val geo2 = GeoCoordinates.newBuilder().setLongitude(53.530556f).setLatitude(9.793333f).build()

    val u1 = UserPosition.newBuilder.setUser("autor").setCertainty(5).setRadius(4).setCenter(geo1).setCreationTime(58).setMessage("dumdidum").setProvenance("twitter")
    val u2 = UserPosition.newBuilder.setUser("autor2").setCertainty(5).setRadius(5).setCenter(geo2).setCreationTime(528).setMessage("dumdidum2").setProvenance("twitter")

    val u = UserPositions.newBuilder.addUserPositions(u1).addUserPositions(u2).setRequestId("7c14bddfaff604ffe7d75311b7cf79d958d71a2e").build
    AkkaMessageBuilder.newBuilder().setType(MessageType.UserPositionsMessage).setSender(foo).setUserPositions(u).build()
  }

  // 
  def testmsg2 = {
    import java.util.Date
    import play.api.libs.json.JsObject
    import com.googlecode.protobuf.format.JsonFormat
    import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage._
    import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.GeoCoordinatesMessage.GeoCoordinates
    import org.haw.cas.web.model.needs.NeedsComponent
    import play.api.libs.json.Json
    val geo1 = GeoCoordinates.newBuilder().setLongitude(53.629982f).setLatitude(10.087782f).build()
    val geo2 = GeoCoordinates.newBuilder().setLongitude(53.530556f).setLatitude(9.793333f).build()

    val u1 = UserPosition.newBuilder.setUser("autor").setCertainty(5).setRadius(4).setCenter(geo1).setCreationTime(58).setMessage("dumdidum").setProvenance("twitter")
    val u2 = UserPosition.newBuilder.setUser("autor2").setCertainty(5).setRadius(5).setCenter(geo2).setCreationTime(528).setMessage("dumdidum2").setProvenance("twitter")

    val u = UserPositions.newBuilder.addUserPositions(u1).addUserPositions(u2).setRequestId("7c14bddfaff604ffe7d75311b7cf79d958d71a2e").build
    AkkaMessageBuilder.newBuilder().setType(MessageType.UserPositionsMessage).setSender(foo).setUserPositions(u).build()
  }

}

object StartRabbitSenderUserPositionsTest extends App {

  val system = ActorSystem("myrabbit")
  val sender = system.actorOf(Props[TestSender], "send")

  RabbitSenderUserPositionsTest.test(sender)

  // Nach 1minute automatisches beenden
  Thread.sleep(60000)
  System.exit(0)

}

