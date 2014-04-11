package org.haw.cas.web.model.needs

import akka.actor.Actor
import akka.event.Logging
import akka.actor.ActorRef
import org.haw.cas.Adapters.rtcadapter.Rabbit
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage.Needs
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateNeedsMessage.UpdateNeeds
import org.haw.cas.web.model.needs.NeedsComponent.Messages._
import org.haw.cas.Adapters.rtcadapter.Subscribe
import org.haw.cas.Adapters.rtcadapter.Topics
import org.haw.cas.web.model.Environment
import org.haw.cas.Adapters.rtcadapter.test.TestEnvironment
import org.haw.cas.Adapters.logging.Log

class NeedsBufferActor extends Actor with Log {

  override def preStart {
    log.debug("NeedsBufferActor started")
    Rabbit.delegator ! Subscribe(Topics.needs)
    Rabbit.delegator ! Subscribe(Topics.broadcast)

    if (Environment.testmessages) {
      TestEnvironment.needs
    }
  }

  def receive = {
    case n: Needs => {
      log.debug("become publishing getAccomodationCount:" +n.getAccomodationCount()+ " getHelperCount: "+n.getHelperCount()+" getDrinkCount: "+n.getDrinkCount()+" getFoodCount:"+n.getFoodCount()+ "getMedicamentCount: "+n.getMedicamentCount()) 
      context.become(publishing(n))
    }
    case _: UpdateNeeds => {
      Rabbit.sender ! metaUpdateNeeds
      context.become(awaiting(List(sender)))
    }
    case _ =>
  }

  def awaiting(list: List[ActorRef]): Receive = {
    case n: Needs => {
      for (ref <- list)
        ref ! n
      log.debug("become publishing getAccomodationCount:" +n.getAccomodationCount()+ " getHelperCount: "+n.getHelperCount()+" getDrinkCount: "+n.getDrinkCount()+" getFoodCount:"+n.getFoodCount()+ "getMedicamentCount: "+n.getMedicamentCount())
      context.become(publishing(n))
    }
    case _: UpdateNeeds => {
      Rabbit.sender ! metaUpdateNeeds
      context.become(awaiting(sender :: list))
    }
    case _ =>
  }

  def publishing(current: Needs): Receive = {
    case n: Needs => {
      log.debug("imcoming in publishing getAccomodationCount:" +n.getAccomodationCount()+ " getHelperCount: "+n.getHelperCount()+" getDrinkCount: "+n.getDrinkCount()+" getFoodCount:"+n.getFoodCount()+ "getMedicamentCount: "+n.getMedicamentCount())
      context.become(publishing(n))
    }
    case _: UpdateNeeds => {
      log.debug("sender in publishing")
      sender ! current
      context.become(publishing(current))
    }
    case _ =>
  }
}