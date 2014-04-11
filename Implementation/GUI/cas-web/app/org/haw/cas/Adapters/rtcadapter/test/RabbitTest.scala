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
import com.typesafe.config.ConfigFactory

object RabbitTest extends App{
 
  val system = ActorSystem("myrabbit")
  
  val sender = system.actorOf(Props[TestSender],"send")
  val consumer = system.actorOf(Props[Consumer],"consumer")

  val foo = system.actorOf(Props[Foo],"foo")
  
  def testmsg: AkkaMessageBuilder = {
    import java.util.Date
    import play.api.libs.json.JsObject
    import com.googlecode.protobuf.format.JsonFormat
    import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage._
    import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.GeoCoordinatesMessage.GeoCoordinates
    import org.haw.cas.web.model.needs.NeedsComponent
    import play.api.libs.json.Json
    val geo1 = GeoCoordinates.newBuilder().setLatitude(9.993333f).setLongitude(9.993333f).build()
    val geo2 = GeoCoordinates.newBuilder().setLatitude(10.993333f).setLongitude(5.993333f).build()
    val acco1 = Accomodation.newBuilder.setAuthor("ich").setCreationTime(System.currentTimeMillis()).setMessage("meine nachricht").setGeo(geo1).build()
    val acco2 = Accomodation.newBuilder.setAuthor("ich2").setCreationTime(System.currentTimeMillis()).setMessage("meine nachricht2").setGeo(geo2).build()
    val helper1 = Helper.newBuilder.setAuthor("ich3").setCreationTime(System.currentTimeMillis()).setMessage("meine nachricht3").setGeo(geo2).build()
    val needs = Needs.newBuilder.addAccomodation(acco1).addAccomodation(acco2).addHelper(helper1).build()
    AkkaMessageBuilder.newBuilder().setType(MessageType.NeedsMessage).setSender(foo.path.toSerializationFormat).setNeeds(needs).build()
  }
}

class Foo extends Actor {
  val log = Logging(context.system, this)
  override def preStart = {
    RabbitTest.sender ! RabbitTest.testmsg
    log.debug(self.path.toString)
  }
  def receive = {
    case m: Message => log.debug("yeha: " + m)
    case m => log.debug("what?" + m)
  }
}
