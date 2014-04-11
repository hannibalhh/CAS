package org.haw.cas.Adapters.rtcadapter

import akka.actor.{ Props, Actor }
import com.rabbitmq.client.QueueingConsumer
import akka.actor.ActorSystem
import akka.actor.actorRef2Scala
import akka.event.Logging
import com.google.protobuf.Message
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.AkkaMessage.AkkaMessageBuilder

class Consumer extends AMQPActor {

  val queue =
    if (productive)
      rabbit.getString("queue.gui.prod")
    else
      rabbit.getString("queue.gui.test")

  protected def startAnonymousListenner() {
    context.actorOf(Props(new Actor {
      def receive = {
        case 'GetMessages =>
          val consumer = new QueueingConsumer(channel)
          log.debug("consume from: " + queue)
          channel.basicConsume(queue, true, consumer)
          while (true) {
            val body = consumer.nextDelivery.getBody
            if (body.length == 0) {
              play.Logger.debug("incoming message is empty")
            } else {
              try {
                Rabbit.delegator ! AkkaMessageBuilder.parseFrom(body)
              } catch {
                case _: Throwable => log.debug("parse of incoming message was not successful")
              }
            }
          }
      }
    })) ! 'GetMessages
  }

  override def preStart() {
    log.debug("started")
    super.preStart()
    startAnonymousListenner()
  }

  def receive = {
    case n => log.debug("oh shit" + n.getClass().getSimpleName())
  }
}
