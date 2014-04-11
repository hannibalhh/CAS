package org.haw.cas.web.model.timeline

import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.Timeline
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.UpdateTimelines
import akka.actor.Actor
import akka.event.Logging
import akka.actor.ActorRef
import org.haw.cas.Adapters.rtcadapter.Rabbit
import org.haw.cas.Adapters.rtcadapter.Subscribe
import org.haw.cas.Adapters.rtcadapter.Topics
import org.haw.cas.web.model.timeline.TimelineComponent._
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.Timelines
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.TimelineType
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.PositionSetting
import org.haw.cas.web.model.Environment
import org.haw.cas.Adapters.rtcadapter.test.TestEnvironment
import org.haw.cas.Adapters.logging.Log

class TimelineBufferActor extends Actor with Log {

  override def preStart {
    log.debug("started")
    Rabbit.delegator ! Subscribe(Topics.timelines)
    Rabbit.delegator ! Subscribe(Topics.broadcast)

    if (Environment.testmessages) {
      TestEnvironment.timeline
    }
  }

  def receive = {
    case t: Timelines => {
      log.debug("new timeline (first, nothing there) ")
      context.become(awaiting(Map(t.getRequestId -> (Nil, Some(t)))))
    }
    case u: UpdateTimelines => {
      log.debug("UpdateTimelines: not found, nothing there ")
      Rabbit.sender ! Messages.meta(u)
      context.become(awaiting(Map(u.getRequestId + "" -> (List(sender), None))))
    }
    case _ =>
  }

  def awaiting(map: Map[String, (List[ActorRef], Option[Timelines])]): Receive = {
    case n: Timelines => {
      if (map.contains(n.getRequestId())) {
        
        val (reflist, option) = map(n.getRequestId())
        log.debug("new timeline (requested), refcount: " + reflist.size)
        for (ref <- reflist)
          ref ! n
      } else
        log.debug("new timeline (not requested) ")
      context.become(awaiting(map + ((n.getRequestId) -> (Nil, Some(n)))))
    }
    case u: UpdateTimelines => {
      log.debug("UpdateTimelines ")
      Rabbit.sender ! Messages.meta(u)
      if (map.contains(u.getRequestId)) {
        val (reflist, option) = map(u.getRequestId)
        val newlist = sender :: reflist
        if (option.isDefined) {
          log.debug("UpdateTimelines: found and defined ")
          for (ref <- newlist)
            ref ! option.get
          context.become(awaiting(map + (u.getRequestId + "" -> (Nil, option))))
        } else{
           log.debug("UpdateTimelines: found and not defined ")
           context.become(awaiting(map + (u.getRequestId + "" -> (newlist, option))))
        }
      } else {
         log.debug("UpdateTimelines: not found")
        context.become(awaiting(map + (u.getRequestId + "" -> (List(sender), None))))
      }
    }
    case _ => 
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