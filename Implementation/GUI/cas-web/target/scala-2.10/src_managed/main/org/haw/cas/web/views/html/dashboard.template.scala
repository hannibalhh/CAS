
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
object dashboard extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template1[String,play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(message: String):play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*1.19*/(""" """),_display_(Seq[Any](/*1.21*/main("Welcome to CAS")/*1.43*/ {_display_(Seq[Any](format.raw/*1.45*/("""

<div data-role="page" id="dashboard" data-title="CAS">
    <div data-role="header" data-tap-toggle="false" data-theme="b" data-position="fixed">
        <a href="#dashboardDialogHelp" data-rel="popup" data-position-to="window" data-iconpos="notext" data-icon="info"></a>
        <h1>Dashboard</h1>
        <a href="#" data-transition="slide" data-icon="phone" data-iconpos="notext" data-iconpos="right"></a>
    </div>

    <div data-role="content">
        <div id="box" data-position="fixxed">
            <fieldset class="ui-grid-f" id="panel">
            <div class="ui-block-a">
                <!-- <label>Chart:</label> -->
                    <select name="select-choice-1"  data-native-menu="false" id="typeChart">
                        <option>Chart Type</option>
                        <option value="area">Area</option>
                        <option value="bar">Bar</option>
                        <option value="column">Column</option>
                        <option value="combo">Combo</option>
                        <option value="line">Line</option>
                        <option value="stepped">Stepped Area</option>
                    </select>
                </div>
                <div class="ui-block-b">
                <!-- <label>Type:</label> -->
                    <select name="select-choice-2"  data-native-menu="false" id="typeChoice">
                        <option>Message Type</option>
                        <option value="All">All</option>
                        <option value="Crevasse">Crevasse</option>
                        <option value="Medicaments">Medicaments</option>
                        <option value="Helper">Helper</option>
                        <option value="Drink">Drink</option>  
                        <option value="Food">Food</option>
                        <option value="Accomodation">Accomodation</option>
                    </select>
                </div>
                <div class="ui-block-c" style="margin-right: 0.3em;">
                <!-- <label>From:</label> -->
                     <input name="split1" type="text" data-role="datebox" readonly='readonly' data-options='"""),format.raw/*40.109*/("""{"""),format.raw/*40.110*/(""""mode": "durationbox", "overrideDateFormat":"%d/%m/%Y", "theme":"b", "themeHeader": "b", "themeDateToday": "a","themeButton": "b""""),format.raw/*40.239*/("""}"""),format.raw/*40.240*/("""' id="time1" />
                </div>
                <div class="ui-block-e">
                <!-- <label>Positionsetting:</label> -->
                    <select name="select-choice-1" data-native-menu="false" id="position1">
                        <option>Positionsetting</option>
                        <option value="Both">Both</option>
                        <option value="With">Only position</option>
                        <option value="Without">Without position</option>
                    </select>
                </div>
                <div class="ui-block-f">
                    <!-- <label>Call to Action:</label> -->
                    <button id="anzeigen" class="ui-btn ui-corner-all ui-btn-b">Show</button>
                </div>
            </fieldset>
            <div id="node" style="height:500px;">
                
            </div>

       <!-- <label id="labelPosition">Positionsetting:</label> -->
    <div class="ui-block-a">
       <select data-theme="b" data-native-menu="false" name="select-choice-b" id="positionMessage">
           <option>Positionsetting</option>
           <option value="Both">Both</option>
           <option value="With">Only position</option>
           <option value="Without">Without position</option>
       </select>
    </div>
       <br>
       <br>
       <br>
        <form id="formSearch">
            <input id="filterTable-input" data-type="search">
        </form>
       <table data-role="table" data-mode="table" class="ui-body-d ui-shadow table-stripe ui-responsive" id="table" data-filter="true" data-input="#filterTable-input">
               <thead><tr class="ui-bar-f"><th data-priority="6">Number</th><th data-priority="1">Author</th> <th data-priority="5">Typ</th> <th data-priority="2">Message</th><th data-priority="3">Provenance</th><th data-priority="4">CreationTime</th></tr></thead>
               <tbody>
         </tbody>
       </table>

        </div>

    <div id="dashboardList" class="dashboardList">
        <div data-role="navbar" data-theme="b">
            <a href="#" id="needs" class="ui-btn ui-btn-b ui-corner-all ui-icon-clock ui-btn-icon-right ui-btn-active">Timeline</a>
            <a href="#" id="messages" class="ui-btn ui-btn-b ui-corner-all ui-icon-mail ui-btn-icon-right ">Messages</a>
        </div>
    </div>

</div>
    <div data-role="footer" data-tap-toggle="false" data-position="fixed" data-theme="b">

        <div data-role="navbar" data-grid="b">
            <ul>
                <li><a href="../" data-ajax="false" data-transition="slide">Current Situation</a>
                </li>
                <li><a href="dashboard" class="ui-btn-active ui-state-persist">Dashboard</a>
                </li>
                <li><a id="login" href="#popupLogin2" data-rel="popup" 
                       data-position-to="window" data-transition="pop">Login</a>
                </li>
            </ul>
        </div>
        <!-- /navbar -->
    </div>
    <!-- footer -->
    <!-- help -->
    <div style="max-width: 500px" data-theme="a" data-overlay-theme="a" id="dashboardDialogHelp" data-role="popup" data-position-to="origin">
        <a href="#" data-rel="back" data-role="button" data-theme="b" data-icon="delete" data-iconpos="notext" class="ui-btn-right">Close</a>
        <div data-theme="b" data-role="header" role="banner">
            <h1>Help</h1>
        </div>
        <div data-theme="a" data-role="content" role="main">
 <div data-role="collapsible-set" data-theme="a" data-content-theme="a">
<div data-role="collapsible" data-theme="b" data-content-theme="b">
    <h4 id="timeLineHelp">Timeline</h4>
     <table data-role="table" id="table-custom-1" class="ui-body-d ui-shadow table-stripe ui-responsive" >        
 <thead>
           <tr class="ui-bar-d">
             <th data-priority="2">Attribute</th>
             <th data-priority="3">Description</th>
           </tr>
         </thead>
         <tbody>
            <tr>
             <th>Chart Type</th>
             <td>Six chart types (Line,Bar,Column Chart...)</td>
           </tr>
            <tr>
             <th>Message Type</th>
             <td>Show all message type or one message type.</td>
           </tr>
           <tr>
             <th>From</th>
             <td>startdate to today date</td>
           </tr>
            <tr>
             <th>Positionsetting</th>
             <td>Both -> Show messages with and without geocordinates.
                 With -> Show messages with geocordinates.
                 Without -> Show messages without geocordinates.</td>
           </tr>
         </tbody>
       </table>
        </div>
        <!-- Collapsible -->
        <div data-role="collapsible" data-theme="b" data-content-theme="b">
    <h4 id="messagesHelp">Messages</h4>
     <table data-role="table" id="table-custom-1" class="ui-body-d ui-shadow table-stripe ui-responsive" >        
 <thead>
           <tr class="ui-bar-d">
             <th data-priority="2">Attribute</th>
             <th data-priority="3">Description</th>
           </tr>
         </thead>
         <tbody>
            <tr>
             <th>Both</th>
             <td>Show messages with and without geocordinates.</td>
           </tr>
            <tr>
             <th>With</th>
             <td>Show messages with geocordinates.</td>
           </tr>
           <tr>
             <th>Without</th>
             <td>Show messages without geocordinates.</td>
           </tr>
         </tbody>
       </table>
        </div>
        <!-- Collapsible -->
    </div>
    <!-- Collapsible Set -->
    </div>
<!-- help -->
    <div data-role="popup" id="popupLogin2" data-theme="a" data-overlay-theme="a" class="ui-corner-all" data-position-to="origin">
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

<div data-role="popup" id="timeOutPopup" data-theme="b" style="max-width:400px;" class="ui-corner-all" data-position-to="window" data-overlay-theme="a">
    <div data-role="header" data-theme="a">
    <h1>Timeout</h1>
    </div>
    <div role="main" class="ui-content">
        <!-- <h3 class="ui-title">Are you sure you want to delete this page?</h3> -->
    <p>No answer from Server. Please try later again.</p>
        <a href="#" class="ui-btn ui-corner-all ui-shadow ui-btn-inline ui-btn-b" data-rel="back">Ok</a>
    </div>
</div>

<div data-role="popup" style=" width:500px;" id="massageSend" data-theme="a" class="ui-corner-all" data-position-to="window" data-overlay-theme="a">
<a href="#" data-rel="back" data-role="button" data-theme="b" data-icon="delete" data-iconpos="notext" class="ui-btn-right">Close</a>
    <form>
        <div style="padding:10px 20px;">
            <label>To:</label>
            <!-- //data-bind="value: author" -->
            <input name="user" id="message" value="" data-theme="a" type="text">
            <label>Message:</label>
            <textarea name="textarea" id="textarea"></textarea>
            <button type="submit" class="ui-btn ui-corner-all ui-shadow ui-btn-b ui-btn-icon-left ui-icon-check">Send</button>
        </div>
    </form>
</div>

</div>
<!-- /page -->
<!-- Google Maps for Map -->
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAHTHlJ5RTiNmYc7j2f8fA-DCGvQI8r6TQ&sensor=false&libraries=weather"></script>
<!-- Google Maps Charts -->
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<!-- Load the Visualization API and the piechart package. -->
<script type="text/javascript">
google.load('visualization', '1.0', """),format.raw/*225.37*/("""{"""),format.raw/*225.38*/("""
    'packages': ['corechart']
"""),format.raw/*227.1*/("""}"""),format.raw/*227.2*/(""");
</script>
<!-- JS Library for Model-View-ViewModel -->
<script src=""""),_display_(Seq[Any](/*230.15*/routes/*230.21*/.Assets.at("javascripts/knockout-3.0.0.js"))),format.raw/*230.64*/(""""></script>
<script src=""""),_display_(Seq[Any](/*231.15*/routes/*231.21*/.Assets.at("javascripts/jquery.tablesorter.min.js"))),format.raw/*231.72*/(""""></script>
<script src=""""),_display_(Seq[Any](/*232.15*/routes/*232.21*/.Assets.at("javascripts/jquery.tablesorter.widgets.min.js"))),format.raw/*232.80*/(""""></script>
<script src=""""),_display_(Seq[Any](/*233.15*/routes/*233.21*/.Assets.at("javascripts/chart.js"))),format.raw/*233.55*/(""""></script>
<script src=""""),_display_(Seq[Any](/*234.15*/routes/*234.21*/.Assets.at("javascripts/needs.js"))),format.raw/*234.55*/(""""></script>
""")))})))}
    }
    
    def render(message:String): play.api.templates.HtmlFormat.Appendable = apply(message)
    
    def f:((String) => play.api.templates.HtmlFormat.Appendable) = (message) => apply(message)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Sun Dec 15 16:46:34 CET 2013
                    SOURCE: /Users/hannibal/Repositories/CAS/Implementation/GUI/cas-web/app/org/haw/cas/web/views/dashboard.scala.html
                    HASH: 68fd7ceea5aba08d6ad4736eda9b290724664734
                    MATRIX: 576->1|687->18|724->20|754->42|793->44|2992->2214|3022->2215|3180->2344|3210->2345|11536->10642|11566->10643|11625->10674|11654->10675|11763->10747|11779->10753|11845->10796|11908->10822|11924->10828|11998->10879|12061->10905|12077->10911|12159->10970|12222->10996|12238->11002|12295->11036|12358->11062|12374->11068|12431->11102
                    LINES: 19->1|22->1|22->1|22->1|22->1|61->40|61->40|61->40|61->40|246->225|246->225|248->227|248->227|251->230|251->230|251->230|252->231|252->231|252->231|253->232|253->232|253->232|254->233|254->233|254->233|255->234|255->234|255->234
                    -- GENERATED --
                */
            