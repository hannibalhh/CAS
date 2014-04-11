
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
object index extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template1[String,play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(message: String):play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*1.19*/(""" """),_display_(Seq[Any](/*1.21*/main("Welcome to CAS")/*1.43*/ {_display_(Seq[Any](format.raw/*1.45*/("""

<div data-role="page" id="karte" data-title="CAS">
    <div data-role="header" data-tap-toggle="false" data-theme="b" data-position="fixed">
        <a href="#popupDialogHelp" data-rel="popup" data-iconpos="notext" data-position-to="window" data-icon="info"></a>
        <h1>Current Situation</h1>
        <a href="#" data-transition="slide" data-icon="phone" data-iconpos="notext"></a>
    </div>

    <div data-role="content">
    <section id="listeViewModel">
        <div id="listleft" class="listLeft">
            <ul id="listleftMarker" data-role="listview" data-split-icon="check" data-theme="c" data-split-theme="b" data-inset="true" data-filter="true" data-filter-placeholder="Needs...">
            </ul>

            <fieldset data-role="controlgroup">
                <button data-theme="b" id="layerReset" data-icon="recycle" data-iconpos="right">Layer</button>
             <!-- <label><input type="checkbox" data-iconpos="right" id="heatbox_box" name="checkbox-1" />Heatmap </label> -->
                <label><input type="checkbox" data-iconpos="right" id="precipitation_box" name="checkbox-2" />Precipitation</label>
                <label><input type="checkbox" data-iconpos="right" id="traffic_box" name="checkbox-3" />Traffic </label>
                <label><input type="checkbox" data-iconpos="right" id="transit_box" name="checkbox-4" />Transit </label>
                <label><input type="checkbox" data-iconpos="right" id="weather_box" name="checkbox-5" />Weather </label>
            </fieldset>
            <!-- https://developers.google.com/places/documentation/supported_types -->
            <select name="select-choice-b"  id="interest" data-theme="b" data-native-menu="false" >
                <option value="all">Places of Interest</option>
                <option value="fire_station">Firestation</option>
                <option value="hospital">Hospital</option>
                <option value="police">Police Station</option>
            </select>
            <!-- class="ui-btn ui-icon-user ui-btn-icon-right ui-shadow-icon ui-btn-b" -->
            <a href="#userPositionDialog" data-rel="popup" class="ui-btn ui-icon-user ui-btn-icon-right ui-shadow-icon ui-btn-b ui-corner-all" >Userposition</a>

        </div>
        </section>
        <!-- Google Map -->
        <div id="map_canvas"></div>
    </div>

    <div data-role="footer" data-tap-toggle="false" data-position="fixed" data-theme="b">

        <div data-role="navbar" data-grid="b">
            <ul>
                <li><a href="#" class="ui-btn-active ui-state-persist">Current Situation</a>
                </li>
                <li><a href="/dashboard">Dashboard</a>
                </li>
                <li><a id="login" href="#popupLogin" data-rel="popup" 
                       data-position-to="window" data-transition="pop">Login</a>
                </li>
            </ul>
        </div>
        <!-- /navbar -->
    </div>
    <!-- footer -->

    <div style="max-width: 500px" data-theme="a" data-overlay-theme="a" id="popupDialogHelp" data-role="popup" data-position-to="orgin">
        <a href="#" data-rel="back" data-role="button" data-theme="b" data-icon="delete" data-iconpos="notext" class="ui-btn-right">Close</a>
        <div data-theme="b" data-role="header" role="banner">
            <h1>Help</h1>
        </div>
        <div data-theme="a" data-role="content" role="main">
 <div data-role="collapsible-set" data-theme="a" data-content-theme="a">
<div data-role="collapsible" data-theme="b" data-content-theme="b">
    <h4 id="markerHelp">Marker</h4>
     <table data-role="table" id="table-custom-1" class="ui-body-d ui-shadow table-stripe ui-responsive" >        
 <thead>
           <tr class="ui-bar-d">
             <th data-priority="2">Marker</th>
             <th>Icon</th>
             <th data-priority="3">Description</th>
           </tr>
         </thead>
         <tbody>
            <tr>
             <th>Accommodation</th>
             <td><img src=""""),_display_(Seq[Any](/*77.29*/routes/*77.35*/.Assets.at("images/marker/housing.png "))),format.raw/*77.75*/("""" alt="Housing"></td>
             <td>Show accommodation for people.</td>
           </tr>
            <tr>
             <th>Authority</th>
             <td><img src=""""),_display_(Seq[Any](/*82.29*/routes/*82.35*/.Assets.at("images/marker/sight.png"))),format.raw/*82.72*/("""" alt="Authority"></td>
             <td>Show Places of Interest.</td>
           </tr>
           <tr>
             <th>Crevasse</th>
             <td><img src=""""),_display_(Seq[Any](/*87.29*/routes/*87.35*/.Assets.at("images/marker/caution.png"))),format.raw/*87.74*/("""" alt="Crevasse"></td>
             <td>Show Crevasse on the map.</td>
           </tr>
           <tr>
             <th>Drink</th>
             <td><img src=""""),_display_(Seq[Any](/*92.29*/routes/*92.35*/.Assets.at("images/marker/coffee.png "))),format.raw/*92.74*/("""" alt="Drink"></td>
             <td>Show People who need drink.</td>
           </tr>
           <tr>
             <th>Food</th>
             <td><img src=""""),_display_(Seq[Any](/*97.29*/routes/*97.35*/.Assets.at("images/marker/steak.png"))),format.raw/*97.72*/("""" alt="Food"></td>
             <td>Show people who need food.</td>
           </tr>
            <tr>
             <th>Helper</th>
             <td><img src=""""),_display_(Seq[Any](/*102.29*/routes/*102.35*/.Assets.at("images/marker/helper.png"))),format.raw/*102.73*/("""" alt="Helper"></td>
             <td>Show people who need help.</td>
           </tr>
           <tr>
             <th>Medicament</th>
             <td><img src=""""),_display_(Seq[Any](/*107.29*/routes/*107.35*/.Assets.at("images/marker/firstaid.png "))),format.raw/*107.76*/("""" alt="Medicament"></td>
             <td>Show people who need medical help.</td>
           </tr>
         </tbody>
       </table>
        </div>
        <!-- Collapsible -->
         <div data-role="collapsible" data-theme="b" data-content-theme="b">
    <h4 id="eventHelp">Event</h4>
     <table data-role="table" id="table-custom-2" class="ui-body-d ui-shadow table-stripe ui-responsive" >        
 <thead>
           <tr class="ui-bar-d">
             <th data-priority="2">Events</th>
             <th data-priority="3">Description</th>
           </tr>
         </thead>
         <tbody>
            <tr>
             <th>Click Marker</th>
             <td>Open infowindow from marker.</td>
           </tr>
            <tr>
             <th>Dbclick Marker</th>
             <td>Closes infowindow and disable marker.</td>
           </tr>
           <tr>
             <th>RightClick Marker</th>
             <td>Closes infowindow and disable marker.</td>
           </tr>
            <tr>
             <th>Swipe Left</th>
             <td>Closes markerlist</td>
           </tr>
            <tr>
             <th>Swipe Right</th>
             <td>Open markerlist</td>
           </tr>
         </tbody>
       </table>
        </div>
    <!-- Collabsible -->
        <div data-role="collapsible" data-theme="b" data-content-theme="b">
    <h4 id="layerHelp">Layer</h4>
     <table data-role="table" id="table-custom-3" class="ui-body-d ui-shadow table-stripe ui-responsive" >        
 <thead>
           <tr class="ui-bar-d">
             <th data-priority="2">Layer</th>
             <th data-priority="3">Description</th>
           </tr>
         </thead>
         <tbody>
            <tr>
             <th>Precipitation</th>
             <td>Show the precipitation of the hole world.</td>
           </tr>
           <tr>
             <th>Traffic</th>
             <td>Google Maps will either show current color-coded traffic conditions on highways and roads where data is available or display a message that data is unavailable. The colors indicate the speed of traffic on the road compared to free-flowing conditions. For highways, green means there is a normal speed of traffic. The more red the roads become, the slower the speed of traffic on the road. Gray indicates there is no data available.

            These speeds don't apply to traffic on smaller roads, such as those within cities, which have lower speed limits. For roads smaller than highways, the colors give an indication of the severity of the traffic. Green means that traffic conditions are good, yellow means fair, and red or red/black means poor traffic conditions.</td>
           </tr>
            <tr>
             <th>Transit</th>
             <td>Types of transit 
            While the transit layer is active, you can view all transit types or limit what is shown to Subway, Bus, Train, or Tram only. This filter is located in the upper right corner of the map.

            Transit lines
            By default, all transit lines are shown for a particular area. Zoom in to see the stops for a certain line. Click on a stop or station (indicated by a blue square) to highlight that line.

            Station pages
            Clicking on a stop or station (indicated by a blue square) will also bring up the station page. This page provides information on which lines stop at that station and when the next transportation will arrive. You can also get directions to that station, see Street view, or view nearby stations.</td>
           </tr>
           <tr>
             <th>Weather</th>
             <td>You can enable the display of weather data or cloud imagery on your map. With temperature in celsius and Windspeed in km per hour.</td>
           </tr>
         </tbody>
       </table>
        </div>
    <!-- Collabsible -->
    </div>
    <!-- Collabsible Set -->
</div>

    </div>

    <div data-role="popup" id="popupLogin" data-theme="a" data-overlay-theme="a" class="ui-corner-all" data-position-to="origin">
    <a href="#" data-rel="back" data-role="button" data-theme="b" data-icon="delete" data-iconpos="notext" class="ui-btn-right">Close</a>
        <form>
            <div style="padding:10px 20px;">
                <h3>Please sign in</h3>
                <label for="un" class="ui-hidden-accessible">Username:</label>
                <input name="user" id="un" value="" placeholder="username" data-theme="a" type="text">
                <label for="pw" class="ui-hidden-accessible">Password:</label>
                <input name="pass" id="pw" value="" placeholder="password" data-theme="a" type="password">
                <!-- type="submit" disabled="true" -->
                <button href="#" class="ui-btn ui-corner-all ui-shadow ui-btn-b ui-btn-icon-left ui-icon-check">Sign in</button>
            </div>
        </form>
    </div>

        <div data-role="popup" style="min-width:300px;"  id="userPositionDialog" data-theme="a" data-overlay-theme="a" class="ui-corner-all" data-position-to="window" >
    <a href="#" data-rel="back" data-role="button" data-theme="b" data-icon="delete" data-iconpos="notext" class="ui-btn-right">Close</a>
        
            <div style="padding:10px 20px;">
                <h3>Userposition</h3> 
                <label>Radius (in km):</label>
                <!-- placeholder="radius" -->
                <input name="user" value="10000" data-theme="a" type="number" id="radius" >
                <label>Since:</label>
                 <input name="split1" type="text" placeholder="since" data-role="datebox" data-options='"""),format.raw/*217.105*/("""{"""),format.raw/*217.106*/(""""mode": "calbox", "overrideDateFormat":"%d/%m/%Y", "themeHeader": "b", "themeDateToday": "a","zindex":1200"""),format.raw/*217.212*/("""}"""),format.raw/*217.213*/("""' id="since1" />
                <!-- type="submit" -->
                <button href="#" id="userPosition" class="ui-btn ui-corner-all ui-shadow ui-btn-b ui-btn-icon-left ui-icon-check">Send</button>
            </div>
        
    </div>

    <div data-role="popup" id="geolocationFailedPopup" data-theme="b" style="max-width:400px;" class="ui-corner-all" data-position-to="window" data-overlay-theme="a">
    <div data-role="header" data-theme="a">
    <h1>New Position</h1>
    </div>
    <div role="main" class="ui-content">
    <p>Geolocation service failed. We've placed you in Hamburg Berliner Tor.</p>
        <a href="#" class="ui-btn ui-corner-all ui-shadow ui-btn-inline ui-btn-b" data-rel="back">Ok</a>
    </div>
</div>

</div>
<!-- /page -->
<!-- Google Maps for Map -->
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAHTHlJ5RTiNmYc7j2f8fA-DCGvQI8r6TQ&sensor=false&libraries=weather,places,visualization"></script>
<!-- Google Maps Charts -->
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<!-- Load the Visualization API and the piechart package. -->
<script type="text/javascript">
google.load('visualization', '1.0', """),format.raw/*242.37*/("""{"""),format.raw/*242.38*/("""
    'packages': ['corechart']
"""),format.raw/*244.1*/("""}"""),format.raw/*244.2*/(""");
</script>
<!-- JS Library for Model-View-ViewModel -->
<script src=""""),_display_(Seq[Any](/*247.15*/routes/*247.21*/.Assets.at("javascripts/knockout-2.2.0.js"))),format.raw/*247.64*/(""""></script>
<!-- add Marker -->
<script src=""""),_display_(Seq[Any](/*249.15*/routes/*249.21*/.Assets.at("javascripts/adMarkerNew.js"))),format.raw/*249.61*/(""""></script>
<!-- Draw the Map -->
<script src=""""),_display_(Seq[Any](/*251.15*/routes/*251.21*/.Assets.at("javascripts/adMap.js"))),format.raw/*251.55*/(""""></script>
<!-- WebSocket-->
<script src=""""),_display_(Seq[Any](/*253.15*/routes/*253.21*/.Assets.at("javascripts/webSocket.js"))),format.raw/*253.59*/(""""></script>
<!-- Layer -->
<script src=""""),_display_(Seq[Any](/*255.15*/routes/*255.21*/.Assets.at("javascripts/thirdPartyLayer.js"))),format.raw/*255.65*/(""""></script>

<script src=""""),_display_(Seq[Any](/*257.15*/routes/*257.21*/.Assets.at("javascripts/pointOfInterest.js"))),format.raw/*257.65*/(""""></script>

<script src=""""),_display_(Seq[Any](/*259.15*/routes/*259.21*/.Assets.at("javascripts/userPosition.js"))),format.raw/*259.62*/(""""></script>

<script src=""""),_display_(Seq[Any](/*261.15*/routes/*261.21*/.Assets.at("javascripts/chart.js"))),format.raw/*261.55*/(""""></script>

<script src=""""),_display_(Seq[Any](/*263.15*/routes/*263.21*/.Assets.at("javascripts/needs.js"))),format.raw/*263.55*/(""""></script>

""")))})),format.raw/*265.2*/("""
"""))}
    }
    
    def render(message:String): play.api.templates.HtmlFormat.Appendable = apply(message)
    
    def f:((String) => play.api.templates.HtmlFormat.Appendable) = (message) => apply(message)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Sun Dec 15 16:46:35 CET 2013
                    SOURCE: /Users/hannibal/Repositories/CAS/Implementation/GUI/cas-web/app/org/haw/cas/web/views/index.scala.html
                    HASH: 367e21631df28e284d49a6e965a5c9b9b76bea68
                    MATRIX: 572->1|683->18|720->20|750->42|789->44|4821->4040|4836->4046|4898->4086|5103->4255|5118->4261|5177->4298|5376->4461|5391->4467|5452->4506|5648->4666|5663->4672|5724->4711|5918->4869|5933->4875|5992->4912|6188->5071|6204->5077|6265->5115|6466->5279|6482->5285|6546->5326|12159->10909|12190->10910|12326->11016|12357->11017|13565->12196|13595->12197|13654->12228|13683->12229|13792->12301|13808->12307|13874->12350|13957->12396|13973->12402|14036->12442|14121->12490|14137->12496|14194->12530|14275->12574|14291->12580|14352->12618|14430->12659|14446->12665|14513->12709|14577->12736|14593->12742|14660->12786|14724->12813|14740->12819|14804->12860|14868->12887|14884->12893|14941->12927|15005->12954|15021->12960|15078->12994|15124->13008
                    LINES: 19->1|22->1|22->1|22->1|22->1|98->77|98->77|98->77|103->82|103->82|103->82|108->87|108->87|108->87|113->92|113->92|113->92|118->97|118->97|118->97|123->102|123->102|123->102|128->107|128->107|128->107|238->217|238->217|238->217|238->217|263->242|263->242|265->244|265->244|268->247|268->247|268->247|270->249|270->249|270->249|272->251|272->251|272->251|274->253|274->253|274->253|276->255|276->255|276->255|278->257|278->257|278->257|280->259|280->259|280->259|282->261|282->261|282->261|284->263|284->263|284->263|286->265
                    -- GENERATED --
                */
            