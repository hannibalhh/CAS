package org.haw.cas.web.model.userpositions

import org.haw.cas.web.model.Environment
import akka.actor.ActorSystem
import akka.actor.ActorRef
import akka.util.Timeout
import akka.actor.Props
import akka.pattern.ask
import scala.concurrent.duration._
import scala.concurrent.Await
import scala.collection.JavaConversions.asScalaBuffer
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.iteratee.Iteratee
import play.api.libs.iteratee.Done
import play.api.libs.iteratee.Enumerator
import play.api.libs.json.JsValue
import play.api.libs.iteratee.Input
import play.api.libs.json._
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.GeoCoordinatesMessage.GeoCoordinates
import org.haw.cas.web.model.userpositions.UserPositionsComponent._
import org.haw.cas.web.model.socket._
import org.haw.cas.Adapters.logging.LowLog

object UserPositionsSocket extends LowLog{

  lazy implicit val timeout = Timeout(100 second)
  lazy val system = Environment.system
  lazy val actor = system.actorOf(Props[UserPositionsSocketActor], "userpositionocketbuffer")
  
  def apply() = {
    (actor ? Messages.updateUserPositions).map {
      case Connect(out) => {
        val in = Iteratee.foreach[JsValue] { event =>   
          actor ! Trigger
          	// there muste be a valid JS Object, because of web socket 
          	// needs a declaration here
        	JsNull
        }
        (in, out) 
      }
      case CannotConnect(error) => {
        debug("CrevassesSocket:errors")
        // A finished Iteratee sending EOF
        val in = Done[JsValue, Unit]((), Input.EOF)
        // Send an error and close the socket
        val out = Enumerator[JsValue](JsObject(Seq("error" -> JsString(error)))).andThen(Enumerator.enumInput(Input.EOF))
        (in, out)
      }
    }
  }
}
