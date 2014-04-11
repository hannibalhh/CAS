// @SOURCE:/Users/hannibal/Repositories/CAS/Implementation/GUI/cas-web/conf/routes
// @HASH:66e6eb47682c6c915e5d4fc4a3a1e4cc60bbc664
// @DATE:Sat Dec 14 14:25:55 CET 2013


import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._


import Router.queryString

object Routes extends Router.Routes {

private var _prefix = "/"

def setPrefix(prefix: String) {
  _prefix = prefix
  List[(String,Routes)]().foreach {
    case (p, router) => router.setPrefix(prefix + (if(prefix.endsWith("/")) "" else "/") + p)
  }
}

def prefix = _prefix

lazy val defaultPrefix = { if(Routes.prefix.endsWith("/")) "" else "/" }


// @LINE:6
private[this] lazy val org_haw_cas_web_controllers_Application_index0 = Route("GET", PathPattern(List(StaticPart(Routes.prefix))))
        

// @LINE:7
private[this] lazy val org_haw_cas_web_controllers_Application_dashboard1 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("dashboard"))))
        

// @LINE:9
private[this] lazy val org_haw_cas_web_controllers_Application_needs2 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("needs"))))
        

// @LINE:10
private[this] lazy val org_haw_cas_web_controllers_Application_needsSocket3 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("needs/socket"))))
        

// @LINE:12
private[this] lazy val org_haw_cas_web_controllers_Application_posts4 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("posts"))))
        

// @LINE:13
private[this] lazy val org_haw_cas_web_controllers_Application_postsSocket5 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("posts/socket"))))
        

// @LINE:16
private[this] lazy val org_haw_cas_web_controllers_Application_crevasses6 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("crevasses"))))
        

// @LINE:17
private[this] lazy val org_haw_cas_web_controllers_Application_crevassesSocket7 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("crevasses/socket"))))
        

// @LINE:19
private[this] lazy val org_haw_cas_web_controllers_Application_timelines8 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("timelines"))))
        

// @LINE:20
private[this] lazy val org_haw_cas_web_controllers_Application_timelinesSocket9 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("timelines/socket"))))
        

// @LINE:22
private[this] lazy val org_haw_cas_web_controllers_Application_userpositions10 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("userpositions"))))
        

// @LINE:23
private[this] lazy val org_haw_cas_web_controllers_Application_userpositionsSocket11 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("userpositions/socket"))))
        

// @LINE:26
private[this] lazy val org_haw_cas_web_controllers_Application_sockettest12 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("sockettest"))))
        

// @LINE:30
private[this] lazy val controllers_Assets_at13 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("assets/"),DynamicPart("file", """.+""",false))))
        
def documentation = List(("""GET""", prefix,"""org.haw.cas.web.controllers.Application.index"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """dashboard""","""org.haw.cas.web.controllers.Application.dashboard"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """needs""","""org.haw.cas.web.controllers.Application.needs"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """needs/socket""","""org.haw.cas.web.controllers.Application.needsSocket"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """posts""","""org.haw.cas.web.controllers.Application.posts"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """posts/socket""","""org.haw.cas.web.controllers.Application.postsSocket"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """crevasses""","""org.haw.cas.web.controllers.Application.crevasses"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """crevasses/socket""","""org.haw.cas.web.controllers.Application.crevassesSocket"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """timelines""","""org.haw.cas.web.controllers.Application.timelines(from:Long, to:Long, ts:String, ps:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """timelines/socket""","""org.haw.cas.web.controllers.Application.timelinesSocket"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """userpositions""","""org.haw.cas.web.controllers.Application.userpositions(la:Float, lo:Float, oldest:Long, radius:Int)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """userpositions/socket""","""org.haw.cas.web.controllers.Application.userpositionsSocket"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """sockettest""","""org.haw.cas.web.controllers.Application.sockettest"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)""")).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
  case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
  case l => s ++ l.asInstanceOf[List[(String,String,String)]] 
}}
      

def routes:PartialFunction[RequestHeader,Handler] = {

// @LINE:6
case org_haw_cas_web_controllers_Application_index0(params) => {
   call { 
        invokeHandler(org.haw.cas.web.controllers.Application.index, HandlerDef(this, "org.haw.cas.web.controllers.Application", "index", Nil,"GET", """ Home page""", Routes.prefix + """"""))
   }
}
        

// @LINE:7
case org_haw_cas_web_controllers_Application_dashboard1(params) => {
   call { 
        invokeHandler(org.haw.cas.web.controllers.Application.dashboard, HandlerDef(this, "org.haw.cas.web.controllers.Application", "dashboard", Nil,"GET", """""", Routes.prefix + """dashboard"""))
   }
}
        

// @LINE:9
case org_haw_cas_web_controllers_Application_needs2(params) => {
   call { 
        invokeHandler(org.haw.cas.web.controllers.Application.needs, HandlerDef(this, "org.haw.cas.web.controllers.Application", "needs", Nil,"GET", """""", Routes.prefix + """needs"""))
   }
}
        

// @LINE:10
case org_haw_cas_web_controllers_Application_needsSocket3(params) => {
   call { 
        invokeHandler(org.haw.cas.web.controllers.Application.needsSocket, HandlerDef(this, "org.haw.cas.web.controllers.Application", "needsSocket", Nil,"GET", """""", Routes.prefix + """needs/socket"""))
   }
}
        

// @LINE:12
case org_haw_cas_web_controllers_Application_posts4(params) => {
   call { 
        invokeHandler(org.haw.cas.web.controllers.Application.posts, HandlerDef(this, "org.haw.cas.web.controllers.Application", "posts", Nil,"GET", """""", Routes.prefix + """posts"""))
   }
}
        

// @LINE:13
case org_haw_cas_web_controllers_Application_postsSocket5(params) => {
   call { 
        invokeHandler(org.haw.cas.web.controllers.Application.postsSocket, HandlerDef(this, "org.haw.cas.web.controllers.Application", "postsSocket", Nil,"GET", """""", Routes.prefix + """posts/socket"""))
   }
}
        

// @LINE:16
case org_haw_cas_web_controllers_Application_crevasses6(params) => {
   call { 
        invokeHandler(org.haw.cas.web.controllers.Application.crevasses, HandlerDef(this, "org.haw.cas.web.controllers.Application", "crevasses", Nil,"GET", """""", Routes.prefix + """crevasses"""))
   }
}
        

// @LINE:17
case org_haw_cas_web_controllers_Application_crevassesSocket7(params) => {
   call { 
        invokeHandler(org.haw.cas.web.controllers.Application.crevassesSocket, HandlerDef(this, "org.haw.cas.web.controllers.Application", "crevassesSocket", Nil,"GET", """""", Routes.prefix + """crevasses/socket"""))
   }
}
        

// @LINE:19
case org_haw_cas_web_controllers_Application_timelines8(params) => {
   call(params.fromQuery[Long]("from", None), params.fromQuery[Long]("to", None), params.fromQuery[String]("ts", None), params.fromQuery[String]("ps", None)) { (from, to, ts, ps) =>
        invokeHandler(org.haw.cas.web.controllers.Application.timelines(from, to, ts, ps), HandlerDef(this, "org.haw.cas.web.controllers.Application", "timelines", Seq(classOf[Long], classOf[Long], classOf[String], classOf[String]),"GET", """""", Routes.prefix + """timelines"""))
   }
}
        

// @LINE:20
case org_haw_cas_web_controllers_Application_timelinesSocket9(params) => {
   call { 
        invokeHandler(org.haw.cas.web.controllers.Application.timelinesSocket, HandlerDef(this, "org.haw.cas.web.controllers.Application", "timelinesSocket", Nil,"GET", """""", Routes.prefix + """timelines/socket"""))
   }
}
        

// @LINE:22
case org_haw_cas_web_controllers_Application_userpositions10(params) => {
   call(params.fromQuery[Float]("la", None), params.fromQuery[Float]("lo", None), params.fromQuery[Long]("oldest", None), params.fromQuery[Int]("radius", None)) { (la, lo, oldest, radius) =>
        invokeHandler(org.haw.cas.web.controllers.Application.userpositions(la, lo, oldest, radius), HandlerDef(this, "org.haw.cas.web.controllers.Application", "userpositions", Seq(classOf[Float], classOf[Float], classOf[Long], classOf[Int]),"GET", """""", Routes.prefix + """userpositions"""))
   }
}
        

// @LINE:23
case org_haw_cas_web_controllers_Application_userpositionsSocket11(params) => {
   call { 
        invokeHandler(org.haw.cas.web.controllers.Application.userpositionsSocket, HandlerDef(this, "org.haw.cas.web.controllers.Application", "userpositionsSocket", Nil,"GET", """""", Routes.prefix + """userpositions/socket"""))
   }
}
        

// @LINE:26
case org_haw_cas_web_controllers_Application_sockettest12(params) => {
   call { 
        invokeHandler(org.haw.cas.web.controllers.Application.sockettest, HandlerDef(this, "org.haw.cas.web.controllers.Application", "sockettest", Nil,"GET", """""", Routes.prefix + """sockettest"""))
   }
}
        

// @LINE:30
case controllers_Assets_at13(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        invokeHandler(controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]),"GET", """ Map static resources from the /public folder to the /assets URL path""", Routes.prefix + """assets/$file<.+>"""))
   }
}
        
}

}
     