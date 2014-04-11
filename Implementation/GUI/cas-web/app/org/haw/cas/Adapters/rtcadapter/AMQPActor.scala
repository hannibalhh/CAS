package org.haw.cas.Adapters.rtcadapter

import akka.actor.Actor
import com.rabbitmq.client.{ Channel, Connection, ConnectionFactory }
import com.typesafe.config.ConfigFactory
import akka.event.Logging
import org.haw.cas.Adapters.logging.Log
abstract class AMQPActor extends Actor with Log{

  
  val queue:String
  val config = ConfigFactory.load()   
  val rabbit = {
    try{
         config.getConfig("rabbit") 
    }
    catch {
      case _:Throwable => {
        log.error("###### RabbitMQ Configuration not found, please configure Application.conf for rabbit.server ####")
        System.exit(-1)
        config       
      }
    }
  }
  val productive = config.getBoolean("application.productive")
  
  import rabbit._
  val hostname = getString("server.hostname")
  val password = getString("server.password")
  val port = getInt("server.port")
  val user = getString("server.user")
  val virtualHost = getString("server.virtual-host")

  val factory = new ConnectionFactory
  factory.setUsername(user)
  factory.setPassword(password)
  factory.setVirtualHost(virtualHost)
  factory.setHost(hostname)
  factory.setPort(port)

  val ConnectionTimeout = getMilliseconds("server.connection-timeout")
  val DeliveryTimeout = getMilliseconds("server.delivery-timeout")
  factory.setConnectionTimeout(ConnectionTimeout.toInt)

  protected var connection: Connection = null
  protected var channel: Channel = null

  override def preStart() {
    try {
    connection = factory.newConnection()
    channel = connection.createChannel()
    channel.queueDeclare(queue, true, false, false, null)
    }
    catch {
      case _:Throwable => {
        log.error("###### RabbitMQ not found ####")
        log.error("###### host: " + hostname +" user: " +user+" port: " +port)
        log.error("###### System schutdown")
        System.exit(-1)
      }
    }
  }
}
