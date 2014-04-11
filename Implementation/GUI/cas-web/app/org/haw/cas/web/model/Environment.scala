package org.haw.cas.web.model

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

object Environment {

  lazy val system = ActorSystem("casweb")
  lazy val config = ConfigFactory.load
  lazy val productive = config.getBoolean("application.productive")
  lazy val testmessages = config.getBoolean("application.testmessages")
}