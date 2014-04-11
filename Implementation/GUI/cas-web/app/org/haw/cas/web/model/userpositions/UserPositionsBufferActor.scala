package org.haw.cas.web.model.userpositions

import akka.actor.Actor
import akka.event.Logging
import akka.actor.ActorRef
import org.haw.cas.Adapters.rtcadapter.Rabbit
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateCrevassesMessage.UpdateCrevasses
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.CrevassesMessage.Crevasses
import org.haw.cas.web.model.userpositions.UserPositionsComponent._
import org.haw.cas.Adapters.rtcadapter.Subscribe
import org.haw.cas.Adapters.rtcadapter.Topics
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UserPositionsMessage.UserPositions
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateUserPositionsMessage.UpdateUserPositions
import org.haw.cas.web.model.Environment
import org.haw.cas.Adapters.rtcadapter.test.TestEnvironment
import org.haw.cas.Adapters.logging.Log

class UserPositionsBufferActor extends Actor with Log {

  override def preStart {
    log.debug("started")
    Rabbit.delegator ! Subscribe(Topics.userPositions)
    Rabbit.delegator ! Subscribe(Topics.broadcast)
       
     if (Environment.testmessages) {
       TestEnvironment.userposition
     }
  }   
  
  def receive = {
    case t: UserPositions => {
      log.debug("new UserPositions (first, nothing there) ")
      context.become(awaiting(Map(t.getRequestId -> (Nil, Some(t)))))
    }
    case u: UpdateUserPositions => {
      log.debug("UpdateUserPositions: not found, nothing there ")
      Rabbit.sender ! Messages.metaUpdateUserPositions(u)
      context.become(awaiting(Map(u.getRequestId + "" -> (List(sender), None))))
    }
    case _ =>
  }

  def awaiting(map: Map[String, (List[ActorRef], Option[UserPositions])]): Receive = {
    case n: UserPositions => {
      if (map.contains(n.getRequestId())) {
        
        val (reflist, option) = map(n.getRequestId())
        log.debug("new UserPositions (requested), refcount: " + reflist.size)
        for (ref <- reflist)
          ref ! n
      } else
        log.debug("new UserPositions (not requested) ")
      context.become(awaiting(map + ((n.getRequestId) -> (Nil, Some(n)))))
    }
    case u: UpdateUserPositions => {
      log.debug("UpdateUserPositions ")
      Rabbit.sender ! Messages.metaUpdateUserPositions(u)
      if (map.contains(u.getRequestId)) {
        val (reflist, option) = map(u.getRequestId)
        val newlist = sender :: reflist
        if (option.isDefined) {
          log.debug("UpdateUserPositions: found and defined ")
          for (ref <- newlist)
            ref ! option.get
          context.become(awaiting(map + (u.getRequestId + "" -> (Nil, option))))
        } else{
           log.debug("UpdateUserPositions: found and not defined ")
           context.become(awaiting(map + (u.getRequestId + "" -> (newlist, option))))
        }
      } else {
         log.debug("UpdateUserPositions: not found")
        context.become(awaiting(map + (u.getRequestId + "" -> (List(sender), None))))
      }
    }
    case _ => log.debug("wrong message")
  }

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    log.debug("restarting")
    context.children foreach { child =>
      context.unwatch(child)
      context.stop(child)
      postStop()      
    }
  }
}