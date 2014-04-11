// @SOURCE:/Users/hannibal/Repositories/CAS/Implementation/GUI/cas-web/conf/routes
// @HASH:66e6eb47682c6c915e5d4fc4a3a1e4cc60bbc664
// @DATE:Sat Dec 14 14:25:55 CET 2013

import Routes.{prefix => _prefix, defaultPrefix => _defaultPrefix}
import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._


import Router.queryString


// @LINE:30
package controllers {

// @LINE:30
class ReverseAssets {
    

// @LINE:30
def at(file:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                                                
    
}
                          
}
                  

// @LINE:26
// @LINE:23
// @LINE:22
// @LINE:20
// @LINE:19
// @LINE:17
// @LINE:16
// @LINE:13
// @LINE:12
// @LINE:10
// @LINE:9
// @LINE:7
// @LINE:6
package org.haw.cas.web.controllers {

// @LINE:26
// @LINE:23
// @LINE:22
// @LINE:20
// @LINE:19
// @LINE:17
// @LINE:16
// @LINE:13
// @LINE:12
// @LINE:10
// @LINE:9
// @LINE:7
// @LINE:6
class ReverseApplication {
    

// @LINE:9
def needs(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "needs")
}
                                                

// @LINE:23
def userpositionsSocket(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "userpositions/socket")
}
                                                

// @LINE:10
def needsSocket(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "needs/socket")
}
                                                

// @LINE:22
def userpositions(la:Float, lo:Float, oldest:Long, radius:Int): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "userpositions" + queryString(List(Some(implicitly[QueryStringBindable[Float]].unbind("la", la)), Some(implicitly[QueryStringBindable[Float]].unbind("lo", lo)), Some(implicitly[QueryStringBindable[Long]].unbind("oldest", oldest)), Some(implicitly[QueryStringBindable[Int]].unbind("radius", radius)))))
}
                                                

// @LINE:7
def dashboard(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "dashboard")
}
                                                

// @LINE:26
def sockettest(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "sockettest")
}
                                                

// @LINE:16
def crevasses(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "crevasses")
}
                                                

// @LINE:13
def postsSocket(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "posts/socket")
}
                                                

// @LINE:19
def timelines(from:Long, to:Long, ts:String, ps:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "timelines" + queryString(List(Some(implicitly[QueryStringBindable[Long]].unbind("from", from)), Some(implicitly[QueryStringBindable[Long]].unbind("to", to)), Some(implicitly[QueryStringBindable[String]].unbind("ts", ts)), Some(implicitly[QueryStringBindable[String]].unbind("ps", ps)))))
}
                                                

// @LINE:20
def timelinesSocket(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "timelines/socket")
}
                                                

// @LINE:12
def posts(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "posts")
}
                                                

// @LINE:17
def crevassesSocket(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "crevasses/socket")
}
                                                

// @LINE:6
def index(): Call = {
   Call("GET", _prefix)
}
                                                
    
}
                          
}
                  


// @LINE:30
package controllers.javascript {

// @LINE:30
class ReverseAssets {
    

// @LINE:30
def at : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                        
    
}
              
}
        

// @LINE:26
// @LINE:23
// @LINE:22
// @LINE:20
// @LINE:19
// @LINE:17
// @LINE:16
// @LINE:13
// @LINE:12
// @LINE:10
// @LINE:9
// @LINE:7
// @LINE:6
package org.haw.cas.web.controllers.javascript {

// @LINE:26
// @LINE:23
// @LINE:22
// @LINE:20
// @LINE:19
// @LINE:17
// @LINE:16
// @LINE:13
// @LINE:12
// @LINE:10
// @LINE:9
// @LINE:7
// @LINE:6
class ReverseApplication {
    

// @LINE:9
def needs : JavascriptReverseRoute = JavascriptReverseRoute(
   "org.haw.cas.web.controllers.Application.needs",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "needs"})
      }
   """
)
                        

// @LINE:23
def userpositionsSocket : JavascriptReverseRoute = JavascriptReverseRoute(
   "org.haw.cas.web.controllers.Application.userpositionsSocket",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "userpositions/socket"})
      }
   """
)
                        

// @LINE:10
def needsSocket : JavascriptReverseRoute = JavascriptReverseRoute(
   "org.haw.cas.web.controllers.Application.needsSocket",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "needs/socket"})
      }
   """
)
                        

// @LINE:22
def userpositions : JavascriptReverseRoute = JavascriptReverseRoute(
   "org.haw.cas.web.controllers.Application.userpositions",
   """
      function(la,lo,oldest,radius) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "userpositions" + _qS([(""" + implicitly[QueryStringBindable[Float]].javascriptUnbind + """)("la", la), (""" + implicitly[QueryStringBindable[Float]].javascriptUnbind + """)("lo", lo), (""" + implicitly[QueryStringBindable[Long]].javascriptUnbind + """)("oldest", oldest), (""" + implicitly[QueryStringBindable[Int]].javascriptUnbind + """)("radius", radius)])})
      }
   """
)
                        

// @LINE:7
def dashboard : JavascriptReverseRoute = JavascriptReverseRoute(
   "org.haw.cas.web.controllers.Application.dashboard",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "dashboard"})
      }
   """
)
                        

// @LINE:26
def sockettest : JavascriptReverseRoute = JavascriptReverseRoute(
   "org.haw.cas.web.controllers.Application.sockettest",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "sockettest"})
      }
   """
)
                        

// @LINE:16
def crevasses : JavascriptReverseRoute = JavascriptReverseRoute(
   "org.haw.cas.web.controllers.Application.crevasses",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "crevasses"})
      }
   """
)
                        

// @LINE:13
def postsSocket : JavascriptReverseRoute = JavascriptReverseRoute(
   "org.haw.cas.web.controllers.Application.postsSocket",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "posts/socket"})
      }
   """
)
                        

// @LINE:19
def timelines : JavascriptReverseRoute = JavascriptReverseRoute(
   "org.haw.cas.web.controllers.Application.timelines",
   """
      function(from,to,ts,ps) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "timelines" + _qS([(""" + implicitly[QueryStringBindable[Long]].javascriptUnbind + """)("from", from), (""" + implicitly[QueryStringBindable[Long]].javascriptUnbind + """)("to", to), (""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("ts", ts), (""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("ps", ps)])})
      }
   """
)
                        

// @LINE:20
def timelinesSocket : JavascriptReverseRoute = JavascriptReverseRoute(
   "org.haw.cas.web.controllers.Application.timelinesSocket",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "timelines/socket"})
      }
   """
)
                        

// @LINE:12
def posts : JavascriptReverseRoute = JavascriptReverseRoute(
   "org.haw.cas.web.controllers.Application.posts",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "posts"})
      }
   """
)
                        

// @LINE:17
def crevassesSocket : JavascriptReverseRoute = JavascriptReverseRoute(
   "org.haw.cas.web.controllers.Application.crevassesSocket",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "crevasses/socket"})
      }
   """
)
                        

// @LINE:6
def index : JavascriptReverseRoute = JavascriptReverseRoute(
   "org.haw.cas.web.controllers.Application.index",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + """"})
      }
   """
)
                        
    
}
              
}
        


// @LINE:30
package controllers.ref {


// @LINE:30
class ReverseAssets {
    

// @LINE:30
def at(path:String, file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]), "GET", """ Map static resources from the /public folder to the /assets URL path""", _prefix + """assets/$file<.+>""")
)
                      
    
}
                          
}
        

// @LINE:26
// @LINE:23
// @LINE:22
// @LINE:20
// @LINE:19
// @LINE:17
// @LINE:16
// @LINE:13
// @LINE:12
// @LINE:10
// @LINE:9
// @LINE:7
// @LINE:6
package org.haw.cas.web.controllers.ref {


// @LINE:26
// @LINE:23
// @LINE:22
// @LINE:20
// @LINE:19
// @LINE:17
// @LINE:16
// @LINE:13
// @LINE:12
// @LINE:10
// @LINE:9
// @LINE:7
// @LINE:6
class ReverseApplication {
    

// @LINE:9
def needs(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   org.haw.cas.web.controllers.Application.needs(), HandlerDef(this, "org.haw.cas.web.controllers.Application", "needs", Seq(), "GET", """""", _prefix + """needs""")
)
                      

// @LINE:23
def userpositionsSocket(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   org.haw.cas.web.controllers.Application.userpositionsSocket(), HandlerDef(this, "org.haw.cas.web.controllers.Application", "userpositionsSocket", Seq(), "GET", """""", _prefix + """userpositions/socket""")
)
                      

// @LINE:10
def needsSocket(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   org.haw.cas.web.controllers.Application.needsSocket(), HandlerDef(this, "org.haw.cas.web.controllers.Application", "needsSocket", Seq(), "GET", """""", _prefix + """needs/socket""")
)
                      

// @LINE:22
def userpositions(la:Float, lo:Float, oldest:Long, radius:Int): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   org.haw.cas.web.controllers.Application.userpositions(la, lo, oldest, radius), HandlerDef(this, "org.haw.cas.web.controllers.Application", "userpositions", Seq(classOf[Float], classOf[Float], classOf[Long], classOf[Int]), "GET", """""", _prefix + """userpositions""")
)
                      

// @LINE:7
def dashboard(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   org.haw.cas.web.controllers.Application.dashboard(), HandlerDef(this, "org.haw.cas.web.controllers.Application", "dashboard", Seq(), "GET", """""", _prefix + """dashboard""")
)
                      

// @LINE:26
def sockettest(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   org.haw.cas.web.controllers.Application.sockettest(), HandlerDef(this, "org.haw.cas.web.controllers.Application", "sockettest", Seq(), "GET", """""", _prefix + """sockettest""")
)
                      

// @LINE:16
def crevasses(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   org.haw.cas.web.controllers.Application.crevasses(), HandlerDef(this, "org.haw.cas.web.controllers.Application", "crevasses", Seq(), "GET", """""", _prefix + """crevasses""")
)
                      

// @LINE:13
def postsSocket(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   org.haw.cas.web.controllers.Application.postsSocket(), HandlerDef(this, "org.haw.cas.web.controllers.Application", "postsSocket", Seq(), "GET", """""", _prefix + """posts/socket""")
)
                      

// @LINE:19
def timelines(from:Long, to:Long, ts:String, ps:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   org.haw.cas.web.controllers.Application.timelines(from, to, ts, ps), HandlerDef(this, "org.haw.cas.web.controllers.Application", "timelines", Seq(classOf[Long], classOf[Long], classOf[String], classOf[String]), "GET", """""", _prefix + """timelines""")
)
                      

// @LINE:20
def timelinesSocket(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   org.haw.cas.web.controllers.Application.timelinesSocket(), HandlerDef(this, "org.haw.cas.web.controllers.Application", "timelinesSocket", Seq(), "GET", """""", _prefix + """timelines/socket""")
)
                      

// @LINE:12
def posts(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   org.haw.cas.web.controllers.Application.posts(), HandlerDef(this, "org.haw.cas.web.controllers.Application", "posts", Seq(), "GET", """""", _prefix + """posts""")
)
                      

// @LINE:17
def crevassesSocket(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   org.haw.cas.web.controllers.Application.crevassesSocket(), HandlerDef(this, "org.haw.cas.web.controllers.Application", "crevassesSocket", Seq(), "GET", """""", _prefix + """crevasses/socket""")
)
                      

// @LINE:6
def index(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   org.haw.cas.web.controllers.Application.index(), HandlerDef(this, "org.haw.cas.web.controllers.Application", "index", Seq(), "GET", """ Home page""", _prefix + """""")
)
                      
    
}
                          
}
        
    