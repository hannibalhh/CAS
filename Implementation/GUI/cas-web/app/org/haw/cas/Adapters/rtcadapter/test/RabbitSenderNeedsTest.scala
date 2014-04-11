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

object RabbitSenderNeedsTest {

  val foo = "needs"

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
    //53.607174,9.826857
    val geo1 = GeoCoordinates.newBuilder().setLatitude(53.629982f).setLongitude(10.087782f).build()
    val geo2 = GeoCoordinates.newBuilder().setLatitude(53.566414f).setLongitude(9.969679f).build()
    val geo3 = GeoCoordinates.newBuilder().setLatitude(53.566414f).setLongitude(9.969679f).build()
    val acco1 = Accomodation.newBuilder.setAuthor("ich").setCreationTime(System.currentTimeMillis()).setMessage("meine nachricht").setGeo(geo1).setProvenance("foo").build()
    val acco2 = Accomodation.newBuilder.setAuthor("ich2").setCreationTime(System.currentTimeMillis()).setMessage("meine nachricht2").setGeo(geo2).setProvenance("foo").build()
    val helper1 = Helper.newBuilder.setAuthor("ich3").setCreationTime(System.currentTimeMillis()).setMessage("meine nachricht3").setGeo(geo3).setProvenance("foo").build()
    val needs = Needs.newBuilder.addAccomodation(acco1).addAccomodation(acco2).addHelper(helper1).build()
    //val needs1 = Needs.newBuilder.addAccomodation(acco2).build()
    AkkaMessageBuilder.newBuilder().setType(MessageType.NeedsMessage).setSender(foo).setNeeds(needs).build()
  }

}

object StartRabbitSenderNeedsTest extends App {

  val system = ActorSystem("myrabbit")
  val sender = system.actorOf(Props[TestSender], "send")

  for (a <- 1 to 10) {
    RabbitSenderNeedsTest.test(sender)
  }

  // Nach 1minute automatisches beenden
  Thread.sleep(60000)
  System.exit(0)

}

