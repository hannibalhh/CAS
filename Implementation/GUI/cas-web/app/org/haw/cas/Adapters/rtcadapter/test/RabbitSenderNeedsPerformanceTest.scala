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
import scala.util.Random
import java.util.Date
import play.api.libs.json.JsObject
import com.googlecode.protobuf.format.JsonFormat
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage._
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.GeoCoordinatesMessage.GeoCoordinates
import org.haw.cas.web.model.needs.NeedsComponent
import play.api.libs.json.Json
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage
object RabbitSenderNeedsPerformanceTest {

  val foo = "needs"
  Random.setSeed(9000L)
  def test(sender: ActorRef) {
    for (i <- 0 to 100) {
      val rand = Math.abs(Random.nextInt() % 500)
//      val rand = 100
      println("messages:"+rand)
      sender ! meta(rand)
      Thread.sleep(5000)
    }
  }

  def geo() = {
    val rand = (Random.nextFloat*15)-7.5f
    val rand2 = (Random.nextFloat*15)-7.5f
    GeoCoordinates.newBuilder().setLatitude(53.629982f + rand).setLongitude(10.087782f + rand2).build
  }

  def meta(n:Int) = AkkaMessageBuilder.newBuilder.
    setType(MessageType.NeedsMessage).setSender(foo).
    setNeeds(testmsg(n-1).build).build

  def testmsg(n: Int = 100, builder: NeedsMessage.Needs.Builder = Needs.newBuilder()): NeedsMessage.Needs.Builder = {
    if (n < 0)
      builder
    else
      testmsg(n - 1, builder.addAccomodation(
        Accomodation.newBuilder.setAuthor("ich").
          setCreationTime(System.currentTimeMillis()).
          setMessage("meine nachricht").setGeo(geo).
          setProvenance("foo").build()))
  }

}

object StartRabbitSenderNeedsPerformanceTest extends App {

  val system = ActorSystem("myrabbit")
  val sender = system.actorOf(Props[TestSender], "send")

  RabbitSenderNeedsPerformanceTest.test(sender)

}

