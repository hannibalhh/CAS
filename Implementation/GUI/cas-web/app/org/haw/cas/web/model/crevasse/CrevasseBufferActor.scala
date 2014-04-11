package org.haw.cas.web.model.crevasse

import akka.actor.Actor
import akka.event.Logging
import akka.actor.ActorRef
import org.haw.cas.Adapters.rtcadapter.Rabbit
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateCrevassesMessage.UpdateCrevasses
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.CrevassesMessage.Crevasses
import org.haw.cas.web.model.crevasse.CrevasseComponent.Messages._
import org.haw.cas.Adapters.rtcadapter.Subscribe
import org.haw.cas.Adapters.rtcadapter.Topics
import org.haw.cas.web.model.Environment
import org.haw.cas.Adapters.rtcadapter.test.TestEnvironment
import org.haw.cas.Adapters.logging.Log

class CrevassesBufferActor extends Actor with Log{

  override def preStart {
    log.debug("started")
    Rabbit.delegator ! Subscribe(Topics.crevasses)
    Rabbit.delegator ! Subscribe(Topics.broadcast)
    
    if (Environment.testmessages) {
      TestEnvironment.crevasse
    }
  }   
  
  def receive = {    
    case n: Crevasses => {
      log.debug("become publishing ")
      context.become(publishing(n)) 
    }
    case _:UpdateCrevasses => {
      Rabbit.sender ! metaUpdateCrevasses
      context.become(awaiting(List(sender)))
    }
    case _ =>
  }
  
  def awaiting(list:List[ActorRef]): Receive = {
    case n:Crevasses => {
      for (ref <- list)
    	  ref ! n
      log.debug("become publishing ")
      context.become(publishing(n)) 
    }
    case _:UpdateCrevasses => {
      Rabbit.sender ! metaUpdateCrevasses
      context.become(awaiting(sender :: list))
    }
    case _ =>
  }
  
  def publishing(current:Crevasses):Receive ={
    case n:Crevasses => {
      log.debug("imcoming in publishing ")
      context.become(publishing(n)) 
    }
    case _:UpdateCrevasses => {
      log.debug(" sender in publishing: " + sender)
      sender ! current
    }  
    case _ =>
  }
}