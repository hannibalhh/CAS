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
import akka.actor.ActorRef

object RabbitSenderCrevasseTest {

  val foo = "crevasses"

  def test(sender: ActorRef) {
    sender ! testmsg
    Thread.sleep(8000)
    sender ! testmsg2
    Thread.sleep(8000)
    sender ! testmsg3
  }
  def testmsg = {
    import java.util.Date
    import play.api.libs.json.JsObject
    import com.googlecode.protobuf.format.JsonFormat
    import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage._
    import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.GeoCoordinatesMessage.GeoCoordinates
    import org.haw.cas.web.model.needs.NeedsComponent
    import play.api.libs.json.Json
    val geo1 = GeoCoordinates.newBuilder().setLatitude(53.550556f).setLongitude(9.993333f).build()
    val c = Crevasse.newBuilder.setAuthor("aaa").setCreationTime(System.currentTimeMillis()).setMessage("Hier ist ein Deich gebrochen!").setGeo(geo1).setProvenance("foo").build()
    val crevasses = Crevasses.newBuilder.addCrevasse(c).build() //.addAccomodation(acco2).addHelper(helper1).build()
    AkkaMessageBuilder.newBuilder().setType(MessageType.CrevassesMessage).setSender(foo).setCrevasses(crevasses).build()
  }

  def testmsg2: AkkaMessageBuilder = {
    import java.util.Date
    import play.api.libs.json.JsObject
    import com.googlecode.protobuf.format.JsonFormat
    import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage._
    import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.GeoCoordinatesMessage.GeoCoordinates
    import org.haw.cas.web.model.needs.NeedsComponent
    import play.api.libs.json.Json
    val geo1 = GeoCoordinates.newBuilder().setLatitude(53.580556f).setLongitude(9.993333f).build()
    val geo2 = GeoCoordinates.newBuilder().setLatitude(53.530556f).setLongitude(9.793333f).build()
    val c1 = Crevasse.newBuilder.setAuthor("bbb").setCreationTime(System.currentTimeMillis()).setMessage("Hier ist ein Deich gebrochen!").setGeo(geo1).setProvenance("foo").build()

    val c2 = Crevasse.newBuilder.setAuthor("cccc").setCreationTime(System.currentTimeMillis()).setMessage("Hier ist ein Deich gebrochen!").setGeo(geo2).setProvenance("foo").build()
    val crevasses = Crevasses.newBuilder.addCrevasse(c1).addCrevasse(c2).build()
    AkkaMessageBuilder.newBuilder().setType(MessageType.CrevassesMessage).setSender(foo).setCrevasses(crevasses).build()
  }

  def testmsg3: AkkaMessageBuilder = {
    import java.util.Date
    import play.api.libs.json.JsObject
    import com.googlecode.protobuf.format.JsonFormat
    import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage._
    import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.GeoCoordinatesMessage.GeoCoordinates
    import org.haw.cas.web.model.needs.NeedsComponent
    import play.api.libs.json.Json
    val geo1 = GeoCoordinates.newBuilder().setLatitude(53.580556f).setLongitude(9.993333f).build()
    val geo2 = GeoCoordinates.newBuilder().setLatitude(53.570556f).setLongitude(8.80000f).build()
    val geo3 = GeoCoordinates.newBuilder().setLatitude(53.520556f).setLongitude(8.5000f).build()

    val c1 = Crevasse.newBuilder.setAuthor("eeee").setCreationTime(System.currentTimeMillis()).setMessage("Hier ist ein Deich gebrochen!").setGeo(geo1).setProvenance("foo").build()

    val c2 = Crevasse.newBuilder.setAuthor("ffff").setCreationTime(System.currentTimeMillis()).setMessage("Hier ist ein Deich gebrochen!").setGeo(geo2).setProvenance("foo").build()

    val c3 = Crevasse.newBuilder.setAuthor("Ivan").setCreationTime(System.currentTimeMillis()).setMessage("Hier ist ein Deich gebrochen!").setGeo(geo3).setProvenance("foo").build()

    val crevasses = Crevasses.newBuilder.addCrevasse(c1).addCrevasse(c2).addCrevasse(c3).build()
    AkkaMessageBuilder.newBuilder().setType(MessageType.CrevassesMessage).setSender(foo).setCrevasses(crevasses).build()
  }

}

object StartRabbitSenderCrevasseTest extends App {

  val system = ActorSystem("myrabbit")
  val sender = system.actorOf(Props[TestSender], "send")

  RabbitSenderCrevasseTest.test(sender)

  // Nach 1minute automatisches beenden
  Thread.sleep(60000)
  System.exit(0)

}

