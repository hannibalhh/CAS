package org.haw.cas.Adapters.rtcadapter
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.actorRef2Scala
import com.google.protobuf.Message
import akka.event.Logging
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.AkkaMessage.AkkaMessageBuilder

class Sender extends AMQPActor {

  val queue = 
   if (productive) 
    rabbit.getString("queue.rtc.prod")
   else
    rabbit.getString("queue.rtc.test")

  def receive = {
    case m: AkkaMessageBuilder => {
      log.debug("send " + m.getType() + " to " +  queue)
      channel.basicPublish("", queue, null, m.toByteArray)
    }
    case m: Message => {
      log.debug("send with wrong type " + m.getClass.getSimpleName + " to " +  queue)
      channel.basicPublish("", queue, null, m.toByteArray)
    }   
  }

  override def postStop {
    channel.close
    connection.close
  }
}
