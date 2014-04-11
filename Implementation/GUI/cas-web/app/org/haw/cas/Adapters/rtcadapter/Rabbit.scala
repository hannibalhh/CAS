package org.haw.cas.Adapters.rtcadapter

import akka.actor.ActorSystem
import akka.actor.Props
import org.haw.cas.web.model.needs.NeedsComponent

object Rabbit {

  lazy val system = ActorSystem("rabbit")
  
  val delegator = system.actorOf(Props[AMQPDelegatorActor],"subscriber")
  lazy val sender = system.actorOf(Props[Sender],"send")
  val consumer = system.actorOf(Props[Consumer],"consumer")  
  
}