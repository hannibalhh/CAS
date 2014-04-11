package org.haw.cas.web.controllers

import play.api._
import play.api.mvc._
import play.api.libs.json.JsValue
import org.haw.cas.web.model.needs.NeedsComponent
import org.haw.cas.web.model.crevasse.CrevasseComponent
import org.haw.cas.web.model.crevasse.CrevassesSocket
import org.haw.cas.web.model.needs.NeedsSocket
import org.haw.cas.web.model.timeline.TimelineComponent
import org.haw.cas.web.model.timeline.TimelineSocket
import org.haw.cas.web.model.userpositions.UserPositionsComponent
import org.haw.cas.web.model.userpositions.UserPositionsSocket
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.TimelineRequest
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.TimelineType
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.PositionSetting
import org.haw.cas.web.model.ErrorComponent
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateUserPositionsMessage.UpdateUserPositions
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.GeoCoordinatesMessage.GeoCoordinates
import org.haw.cas.web.model.HashingComponent
import org.haw.cas.web.model.posts.PostsComponent
import org.haw.cas.web.model.posts.PostsSocket
import org.haw.cas.Adapters.logging.LowLog

object Application extends Controller with LowLog{

  def sockettest = Action {
    Ok(org.haw.cas.web.views.html.sockettest(""))
  }

  def index = Action {
    Ok(org.haw.cas.web.views.html.index(""))
  }

  def dashboard = Action {
    Ok(org.haw.cas.web.views.html.dashboard(""))
  }

  /**
   *  ajax needs request
   *  classic ajax json
   *  hamburg fallback
   */
  def needs = Action {
    Ok(NeedsComponent())
  }

  /**
   * Handles websocket for needs messages.
   */
  def needsSocket = WebSocket.async[JsValue] { request =>
    NeedsSocket()
  }
  
    /**
   *  ajax posts request
   *  classic ajax json
   *  hamburg fallback
   */
  def posts = Action {
    Ok(PostsComponent())
  }

  /**
   * Handles websocket for needs messages.
   */
  def postsSocket = WebSocket.async[JsValue] { request =>
    PostsSocket()
  }

  /**
   *  ajax crevasses request
   *  classic ajax json
   *  hamburg fallback
   */
  def crevasses = Action {
    Ok(CrevasseComponent())
  }

  /**
   * Handles websocket for crevasses messages.
   */
  def crevassesSocket = WebSocket.async[JsValue] { request =>
    CrevassesSocket()
  }

  /**
   *  ajax timelines request
   *  classic ajax json
   *  hamburg fallback
   */
  def timelines(from: Long, to: Long, ts: String, ps: String) = Action {
    try {
      val p = PositionSetting.valueOf(ps)
      if (ts.equals("All")) {
        var list = List[TimelineRequest]()
        for (t <- TimelineType.values()){
        	val tm = TimelineRequest.newBuilder.setType(t).setPositionSetting(p).build
        	list = tm :: list		
        }
        Ok(TimelineComponent(list,from,to))
      } else {
        val t = TimelineType.valueOf("T"+ts)
        val tm = TimelineRequest.newBuilder.setType(t).setPositionSetting(p).build
        Ok(TimelineComponent(List(tm),from,to))
      }
    } catch {
      case x: Throwable => {
        debug("timelines: " + x)
        Ok(ErrorComponent.message("wrong datatypes"))
      }
    }
  }

  /**
   * Handles websocket for timeline messages.
   */
  def timelinesSocket = WebSocket.async[JsValue] { request =>
    TimelineSocket()
  }

  /**
   *  ajax userpositions request
   *  classic ajax json
   *  hamburg fallback
   */
  def userpositions(la: Float, lo: Float, oldest: Long, radius: Int) = Action {
    try {
      val temp: UpdateUserPositions = UpdateUserPositions.newBuilder.setCenter(GeoCoordinates.newBuilder.setLatitude(la).setLongitude(lo).build).setOldest(oldest).setRadius(radius).setRequestId("").build
      val hash = HashingComponent.hash(temp.toString)
      debug("new userposition request id: " + hash)
      val u: UpdateUserPositions = UpdateUserPositions.newBuilder.setCenter(GeoCoordinates.newBuilder.setLatitude(la).setLongitude(lo).build).setOldest(oldest).setRadius(radius).setRequestId(hash).build
      Ok(UserPositionsComponent(u))
    } catch {
      case x: Throwable => {
        debug("userpositions: " + x)
        Ok(ErrorComponent.message("wrong datatypes"))
      }
    }
  }

  /**
   * Handles websocket for userpositions messages.
   */
  def userpositionsSocket = WebSocket.async[JsValue] { request =>
    UserPositionsSocket()
  }
}