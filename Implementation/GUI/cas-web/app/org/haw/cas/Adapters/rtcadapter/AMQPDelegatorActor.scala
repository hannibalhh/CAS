package org.haw.cas.Adapters.rtcadapter

import akka.actor.Actor
import akka.actor.ActorRef
import com.google.protobuf.Message
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.AkkaMessage.AkkaMessageBuilder
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.AkkaMessage.AkkaMessageBuilder.MessageType
import akka.event.Logging
import org.haw.cas.Adapters.logging.Log

case class Subscribe(topic: Topics.Value)
case class Unsubscribe(topic: Topics.Value)

class AMQPDelegatorActor extends Actor with Log{
  
  override def preStart = {
    log.debug("started")
  }

  def receive = {
    case Subscribe(topic) => context.become(fetching(Map(topic -> List(sender))))
    case Unsubscribe(topic) => // nothing do here  
    case m: Message => log.debug("nothing is subscribed")
  }

  def fetching(subscriber: Map[Topics.Value, List[ActorRef]]): Receive = {
    case Subscribe(topic) if (!subscriber.contains(topic)) => {
      context.become(fetching(subscriber + (topic -> List(sender))))
    }
    case Subscribe(topic) => {  
      context.become(fetching(subscriber +
        (topic -> (sender :: subscriber(topic)))))
    }

    case meta: AkkaMessageBuilder => {
      if (Topics.contains(meta.getSender)){
	      val topic = Topics.withName(meta.getSender)
	      if (topic == Topics.broadcast){
	        log.debug("broadcast:broadcast ist deprecated, please use broadcast for the defined service")
	      }
	      if (subscriber.contains(topic)) {
	        val m = message(meta)
	        for (ref <- subscriber(topic))
	          ref ! m
	      } else
	        log.debug("nothing is for subscribed for topic : " + topic)
      }
      else{
        log.debug("topic not supported: " + meta.getSender)
      }
    }

    case Unsubscribe(topic) => // TODO not implemented yet
  }

  def message(meta: AkkaMessageBuilder): Message = {
    meta.getType match {
      case MessageType.CrevassesMessage => meta.getCrevasses
      case MessageType.NeedsMessage => meta.getNeeds
      case MessageType.TimelinesMessage => meta.getTimelines
      case MessageType.UserPositionsMessage => meta.getUserPositions
      case MessageType.PostsMessage => meta.getPosts
    }
  }

}