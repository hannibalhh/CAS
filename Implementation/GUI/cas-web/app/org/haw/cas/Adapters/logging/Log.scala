package org.haw.cas.Adapters.logging

import akka.actor.Actor
import akka.event.Logging

trait LowLog {
  def debug(s:String) = {
    play.Logger.debug("[std] " + s)
    println(s)
  }
}

trait Log extends Actor {
	val akkalog = Logging(context.system.eventStream, this)
	val log = new LogAdapter()
	class LogAdapter {
		def debug(s:String) = {
		  akkalog.debug(s)
		  play.Logger.debug("["+self.path+"] " + s)
		}
		def error(s:String) = {
		  akkalog.error(s)
		  play.Logger.error("["+self.path+"] " + s)
		}
	}
}