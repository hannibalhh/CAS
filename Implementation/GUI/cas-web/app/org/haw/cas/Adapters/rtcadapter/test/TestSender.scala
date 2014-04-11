package org.haw.cas.Adapters.rtcadapter.test
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.actorRef2Scala
import com.google.protobuf.Message
import akka.event.Logging
import org.haw.cas.Adapters.rtcadapter.AMQPActor
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.AkkaMessage.AkkaMessageBuilder

class TestSender extends AMQPActor {

  val queue = 
   if (productive) 
    rabbit.getString("queue.gui.prod")
   else
    rabbit.getString("queue.gui.test")

  def receive = {
    case m: AkkaMessageBuilder => {
      log.debug("send "  + m.getType() + " to " +  queue)
      channel.basicPublish("", queue, null, m.toByteArray)
    }
    case m:Array[Byte] => {
      log.debug("what? " + m)
      channel.basicPublish("", queue, null, m)
    }
  }

  override def postStop {
    channel.close
    connection.close
  }
}
