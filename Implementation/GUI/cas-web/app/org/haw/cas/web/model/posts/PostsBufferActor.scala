package org.haw.cas.web.model.posts

import akka.actor.Actor
import akka.event.Logging
import akka.actor.ActorRef
import org.haw.cas.Adapters.rtcadapter.Rabbit
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.PostsMessage.Posts
import org.haw.cas.web.model.posts.PostsComponent.Messages._
import org.haw.cas.Adapters.rtcadapter.Subscribe
import org.haw.cas.Adapters.rtcadapter.Topics
import org.haw.cas.web.model.Environment
import org.haw.cas.Adapters.rtcadapter.test.TestEnvironment
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.PostsMessage.UpdatePosts
import org.haw.cas.Adapters.logging.Log

class PostsBufferActor extends Actor with Log{

  override def preStart {
    log.debug("started")
    Rabbit.delegator ! Subscribe(Topics.posts)
    Rabbit.delegator ! Subscribe(Topics.broadcast)
    
    if (Environment.testmessages) {
      TestEnvironment.posts
    }
  }   
  
  def receive = {    
    case n: Posts => {
      log.debug("become publishing ")
      context.become(publishing(n)) 
    }
    case _:UpdatePosts => {
      Rabbit.sender ! metaUpdatePosts
      context.become(awaiting(List(sender)))
    }
    case _ =>
  }
  
  def awaiting(list:List[ActorRef]): Receive = {
    case n:Posts => {
      for (ref <- list)
    	  ref ! n
      log.debug("become publishing ")
      context.become(publishing(n)) 
    }
    case _:UpdatePosts => {
      Rabbit.sender ! metaUpdatePosts
      context.become(awaiting(sender :: list))
    }
    case _ =>
  }
  
  def publishing(current:Posts):Receive ={
    case n:Posts => {
      log.debug("imcoming in publishing ")
      context.become(publishing(n)) 
    }
    case _:UpdatePosts => {
      Rabbit.sender ! metaUpdatePosts
      log.debug(" sender in publishing")
      sender ! current
    }  
    case _ =>
  }
}