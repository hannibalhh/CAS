
package org.haw.cas.web.views.html

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import play.api.i18n._
import play.api.mvc._
import play.api.data._
import views.html._
/**/
object sockettest extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template1[String,play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(message: String):play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*1.19*/(""" """),format.raw/*1.20*/("""{"""),format.raw/*1.21*/("""

<!DOCTYPE html>  
<meta charset="utf-8" />  
<title>WebSocket Test</title>  
<script language="javascript" type="text/javascript">  
var wsUri = "ws://localhost:9000/crevasses/socket"; 
var output;  function init() """),format.raw/*8.30*/("""{"""),format.raw/*8.31*/(""" 
	output = document.getElementById("output"); 
	testWebSocket(); 
"""),format.raw/*11.1*/("""}"""),format.raw/*11.2*/("""  
function testWebSocket() """),format.raw/*12.26*/("""{"""),format.raw/*12.27*/(""" 
		websocket = new WebSocket(wsUri); 
		websocket.onopen = function(evt) """),format.raw/*14.36*/("""{"""),format.raw/*14.37*/(""" onOpen(evt) """),format.raw/*14.50*/("""}"""),format.raw/*14.51*/("""; 
		websocket.onclose = function(evt) """),format.raw/*15.37*/("""{"""),format.raw/*15.38*/(""" onClose(evt) """),format.raw/*15.52*/("""}"""),format.raw/*15.53*/("""; 
		websocket.onmessage = function(evt) """),format.raw/*16.39*/("""{"""),format.raw/*16.40*/(""" onMessage(evt) """),format.raw/*16.56*/("""}"""),format.raw/*16.57*/("""; 
		websocket.onerror = function(evt) """),format.raw/*17.37*/("""{"""),format.raw/*17.38*/(""" onError(evt) """),format.raw/*17.52*/("""}"""),format.raw/*17.53*/("""; """),format.raw/*17.55*/("""}"""),format.raw/*17.56*/("""  
	function onOpen(evt) """),format.raw/*18.23*/("""{"""),format.raw/*18.24*/(""" writeToScreen(evt); 
	"""),format.raw/*19.2*/("""}"""),format.raw/*19.3*/("""  
	function onClose(evt) """),format.raw/*20.24*/("""{"""),format.raw/*20.25*/(""" 
		writeToScreen("DISCONNECTED"); 
	"""),format.raw/*22.2*/("""}"""),format.raw/*22.3*/("""  
	function onMessage(evt) """),format.raw/*23.26*/("""{"""),format.raw/*23.27*/(""" 
		writeToScreen('<span style="color: blue;">RESPONSE: ' + evt.data+'</span>'); 
	"""),format.raw/*25.2*/("""}"""),format.raw/*25.3*/("""  
	function onError(evt) """),format.raw/*26.24*/("""{"""),format.raw/*26.25*/(""" writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data); """),format.raw/*26.96*/("""}"""),format.raw/*26.97*/("""  
		function doSend(message) """),format.raw/*27.28*/("""{"""),format.raw/*27.29*/(""" 
			writeToScreen("SENT: " + message);  
		websocket.send(message); """),format.raw/*29.28*/("""}"""),format.raw/*29.29*/("""  
		function writeToScreen(message) """),format.raw/*30.35*/("""{"""),format.raw/*30.36*/(""" 
			var pre = document.createElement("p"); 
			pre.style.wordWrap = "break-word"; 
			pre.innerHTML = message; 
			output.appendChild(pre); """),format.raw/*34.29*/("""}"""),format.raw/*34.30*/("""  
		window.addEventListener("load", init, false);  
		</script> 
<h2>WebSocket Test</h2>  
<div id="output"></div>
</html>
"""),format.raw/*40.1*/("""}"""),format.raw/*40.2*/("""
"""))}
    }
    
    def render(message:String): play.api.templates.HtmlFormat.Appendable = apply(message)
    
    def f:((String) => play.api.templates.HtmlFormat.Appendable) = (message) => apply(message)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Sat Dec 14 14:26:01 CET 2013
                    SOURCE: /Users/hannibal/Repositories/CAS/Implementation/GUI/cas-web/app/org/haw/cas/web/views/sockettest.scala.html
                    HASH: 82d0f97eda1e6acc4374db3b8dfd43a728a7d365
                    MATRIX: 577->1|688->18|716->19|744->20|988->237|1016->238|1110->305|1138->306|1194->334|1223->335|1325->409|1354->410|1395->423|1424->424|1491->463|1520->464|1562->478|1591->479|1660->520|1689->521|1733->537|1762->538|1829->577|1858->578|1900->592|1929->593|1959->595|1988->596|2041->621|2070->622|2120->645|2148->646|2202->672|2231->673|2295->710|2323->711|2379->739|2408->740|2518->823|2546->824|2600->850|2629->851|2728->922|2757->923|2815->953|2844->954|2941->1023|2970->1024|3035->1061|3064->1062|3233->1203|3262->1204|3413->1328|3441->1329
                    LINES: 19->1|22->1|22->1|22->1|29->8|29->8|32->11|32->11|33->12|33->12|35->14|35->14|35->14|35->14|36->15|36->15|36->15|36->15|37->16|37->16|37->16|37->16|38->17|38->17|38->17|38->17|38->17|38->17|39->18|39->18|40->19|40->19|41->20|41->20|43->22|43->22|44->23|44->23|46->25|46->25|47->26|47->26|47->26|47->26|48->27|48->27|50->29|50->29|51->30|51->30|55->34|55->34|61->40|61->40
                    -- GENERATED --
                */
            