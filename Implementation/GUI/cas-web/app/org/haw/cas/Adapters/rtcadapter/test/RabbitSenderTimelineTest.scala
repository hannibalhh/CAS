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
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.Timeline
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.Timelines
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.Point
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.TimelineType
import org.haw.cas.web.model.timeline.TimelineComponent
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.PositionSetting
import akka.actor.ActorRef

object RabbitSenderTimelineTest {

  val foo = "timelines"
  // http://localhost:9000/timelines?from=0&to=1&ts=Crevasse&ps=Both
  def test(sender: ActorRef) {
    sender ! testmsg
    sender ! testmsg1
    sender ! testmsg2
    sender ! testmsg3
  }

  // 5-5.11 both crevasse
  def testmsg = {
    import java.util.Date
    import play.api.libs.json.JsObject
    import com.googlecode.protobuf.format.JsonFormat
    import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage._
    import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.GeoCoordinatesMessage.GeoCoordinates
    import org.haw.cas.web.model.needs.NeedsComponent
    import play.api.libs.json.Json

    val p1 = Point.newBuilder.setX(5).setY(5).build
    val p2 = Point.newBuilder.setX(3).setY(3).build
    val p3 = Point.newBuilder.setX(10).setY(8).build
    val p4 = Point.newBuilder.setX(12).setY(12).build
    val p5 = Point.newBuilder.setX(10000000).setY(11).build

    val t = Timeline.newBuilder.addDataPoints(p1).addDataPoints(p2).addDataPoints(p3).addDataPoints(p4).addDataPoints(p5).setType(TimelineType.TAccomodation).setHasValues(true).build

    val timelines = Timelines.newBuilder.addTimelines(t).setRequestId("bec83afbfc27c56ebb9c9487a28408a3f700a912").build()

    AkkaMessageBuilder.newBuilder().setType(MessageType.TimelinesMessage).setSender(foo).setTimelines(timelines).build()
  }

  // 12-28.11 both crevasse
  def testmsg1 = {
    import java.util.Date
    import play.api.libs.json.JsObject
    import com.googlecode.protobuf.format.JsonFormat
    import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage._
    import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.GeoCoordinatesMessage.GeoCoordinates
    import org.haw.cas.web.model.needs.NeedsComponent
    import play.api.libs.json.Json

    val p1 = Point.newBuilder.setX(29).setY(8).build
    val p2 = Point.newBuilder.setX(3).setY(12).build
    val p3 = Point.newBuilder.setX(30).setY(3).build
    val p4 = Point.newBuilder.setX(12).setY(12).build
    val p5 = Point.newBuilder.setX(100).setY(1).build

    val t = Timeline.newBuilder.addDataPoints(p1).addDataPoints(p2).addDataPoints(p3).addDataPoints(p4).addDataPoints(p5).setType(TimelineType.TAccomodation).setHasValues(true).build
    val timelines = Timelines.newBuilder.addTimelines(t).setRequestId("bec83afbfc27c56ebb9c9487a28408a3f700a912").build()
    AkkaMessageBuilder.newBuilder().setType(MessageType.TimelinesMessage).setSender(foo).setTimelines(timelines).build()
  }

  // 08.05-11.11 both crevasse
  def testmsg2 = {
    import java.util.Date
    import play.api.libs.json.JsObject
    import com.googlecode.protobuf.format.JsonFormat
    import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage._
    import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.GeoCoordinatesMessage.GeoCoordinates
    import org.haw.cas.web.model.needs.NeedsComponent
    import play.api.libs.json.Json

    val p1 = Point.newBuilder.setX(29).setY(2).build
    val p2 = Point.newBuilder.setX(3000).setY(5).build
    val p3 = Point.newBuilder.setX(120030).setY(30).build
    val p4 = Point.newBuilder.setX(1200012).setY(50).build
    val p5 = Point.newBuilder.setX(130000100).setY(20).build

    val t = Timeline.newBuilder.addDataPoints(p1).addDataPoints(p2).addDataPoints(p3).addDataPoints(p4).addDataPoints(p5).setType(TimelineType.TAccomodation).setHasValues(true).build
    val timelines = Timelines.newBuilder.addTimelines(t).setRequestId("e82ed9c5e8570ac674a5819bcd174216ebc4386a").build()
    AkkaMessageBuilder.newBuilder().setType(MessageType.TimelinesMessage).setSender(foo).setTimelines(timelines).build()
  } 
 // dreckmist
  // 08.05-11.11 both all
  // http://localhost:9000/timelines?from=0&to=1&ts=All&ps=Both
  def testmsg3 = {
    import java.util.Date
    import play.api.libs.json.JsObject
    import com.googlecode.protobuf.format.JsonFormat
    import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage._
    import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.GeoCoordinatesMessage.GeoCoordinates
    import org.haw.cas.web.model.needs.NeedsComponent
    import play.api.libs.json.Json

    val timelines = Timelines.newBuilder.setRequestId("c27eb4b7ca59b7259ab21bb7c321fdf709ba5b65")
    var x = 3
    for (typ <- TimelineType.values()) {
      val p1 = Point.newBuilder.setX(29).setY(2 + x).build
      val p2 = Point.newBuilder.setX(3000).setY(5 + x).build
      val p3 = Point.newBuilder.setX(120030).setY(30 + x).build
      val p4 = Point.newBuilder.setX(1200012).setY(50 + x).build
      val p5 = Point.newBuilder.setX(130000100).setY(20 + x).build
      val t = Timeline.newBuilder.addDataPoints(p1).addDataPoints(p2).addDataPoints(p3).addDataPoints(p4).addDataPoints(p5).setType(typ).setHasValues(true).build
      timelines.addTimelines(t)
      x += 1
    }
    AkkaMessageBuilder.newBuilder().setType(MessageType.TimelinesMessage).setSender(foo).setTimelines(timelines.build).build()
  }

  // 08.05-11.11 both all
  // http://localhost:9000/timelines?from=0&to=1&ts=All&ps=Both
  def testmsg3notvalid = {
    import java.util.Date
    import play.api.libs.json.JsObject
    import com.googlecode.protobuf.format.JsonFormat
    import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage._
    import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.GeoCoordinatesMessage.GeoCoordinates
    import org.haw.cas.web.model.needs.NeedsComponent
    import play.api.libs.json.Json

    val timelines = Timelines.newBuilder.setRequestId("c27eb4b7ca59b7259ab21bb7c321fdf709ba5b65")
    var x = 3
    for (typ <- TimelineType.values()) {
      val p1 = Point.newBuilder.setX(29).setY(2 + x).build
      val p2 = Point.newBuilder.setX(3000).setY(5 + x).build
      val p3 = Point.newBuilder.setX(120030).setY(30 + x).build
      val p4 = Point.newBuilder.setX(1200012).setY(50 + x).build
      val p5 = Point.newBuilder.setX(130000100).setY(20 + x).build
      val t = if (x % 2 == 0)
        Timeline.newBuilder.addDataPoints(p1).addDataPoints(p2).addDataPoints(p3).addDataPoints(p4).addDataPoints(p5).setType(typ).setHasValues(true).build
      else
        Timeline.newBuilder.addDataPoints(p2).addDataPoints(p3).addDataPoints(p4).addDataPoints(p5).setType(typ).setHasValues(true).build

      timelines.addTimelines(t)
      x += 1
    }
    AkkaMessageBuilder.newBuilder().setType(MessageType.TimelinesMessage).setSender(foo).setTimelines(timelines.build).build()
  }
}

object StartRabbitSenderTimelineTest extends App {

  val system = ActorSystem("myrabbit")
  val sender = system.actorOf(Props[TestSender], "send")

  RabbitSenderTimelineTest.test(sender)

  // Nach 1minute automatisches beenden
  Thread.sleep(60000)
  System.exit(0)

}

