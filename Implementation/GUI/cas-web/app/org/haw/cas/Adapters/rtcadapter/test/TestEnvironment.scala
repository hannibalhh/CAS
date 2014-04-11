package org.haw.cas.Adapters.rtcadapter.test

import akka.actor.ActorSystem
import akka.actor.Props

object TestEnvironment {
  
  val system = ActorSystem("myrabbit")
  val sender = system.actorOf(Props[TestSender], "send")
  
  def needs = RabbitSenderNeedsTest.test(sender) 
  def crevasse = RabbitSenderCrevasseTest.test(sender)
  def timeline = RabbitSenderTimelineTest.test(sender)
  def userposition = RabbitSenderUserPositionsTest.test(sender)  
  def posts = RabbitSenderPostsTest.test(sender)
  
  def test = {
      needs
      crevasse
      timeline
      userposition  
      posts
  }
}

object StartTestEnvironment extends App {
  TestEnvironment.test
}