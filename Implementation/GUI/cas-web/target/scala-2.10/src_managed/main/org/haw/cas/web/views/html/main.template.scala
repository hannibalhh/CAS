
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
object main extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template2[String,Html,play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(title: String)(content: Html):play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*1.32*/("""

<!DOCTYPE html>

<html>
    <head>
        <title>"""),_display_(Seq[Any](/*7.17*/title)),format.raw/*7.22*/("""</title>

	    <meta name="format-detection" content="telephone=no" />
		<meta name="viewport" content="width=device-width, user-scalable=yes">
		<meta charset="utf-8">
		<!-- Fullscreen Webapp -->
		<meta name="apple-mobile-web-app-capable" content="yes">
		
		<!--Sets whether a web application runs in full-screen mode-->
		<meta name="apple-mobile-web-app-capable" content="yes" />
		
    	<!-- Icon for Tab -->
    	<link href=""""),_display_(Seq[Any](/*19.19*/routes/*19.25*/.Assets.at("images/flood.ico"))),format.raw/*19.55*/("""" rel="icon" type="image/x-icon"/>

		<!-- Theme jQuery Mobile -->
		<link rel="stylesheet" href=""""),_display_(Seq[Any](/*22.33*/routes/*22.39*/.Assets.at("stylesheets/themes/jquery.mobile-1.4.0-rc.1.min.css"))),format.raw/*22.104*/("""" />
		
		<!-- Eigene Icons, Hintergrundbild -->
		<link rel="stylesheet" href=""""),_display_(Seq[Any](/*25.33*/routes/*25.39*/.Assets.at("stylesheets/styles.css"))),format.raw/*25.75*/("""" />
		
		<link rel="stylesheet" type="text/css" href="http://dev.jtsage.com/cdn/datebox/latest/jqm-datebox.min.css" />
		<!-- jQuery -->
		<script src=""""),_display_(Seq[Any](/*29.17*/routes/*29.23*/.Assets.at("javascripts/jquery/jquery-1.10.2.min.js"))),format.raw/*29.76*/(""""></script> 	

		<!-- Globale Definition von HeaderTheme, FooterTheme etc. -->
		<script src=""""),_display_(Seq[Any](/*32.17*/routes/*32.23*/.Assets.at("javascripts/globalDefinations.js"))),format.raw/*32.69*/(""""></script>
		
		<!-- jQuery Mobile -->
		<script src=""""),_display_(Seq[Any](/*35.17*/routes/*35.23*/.Assets.at("javascripts/jquery_mobile/jquery.mobile-1.4.0-rc.1.min.js"))),format.raw/*35.94*/(""""></script> 

		<script type="text/javascript" src="http://dev.jtsage.com/cdn/datebox/latest/jqm-datebox.core.min.js"></script>
	
		<script src=""""),_display_(Seq[Any](/*39.17*/routes/*39.23*/.Assets.at("javascripts/jqm-datebox.mode.durationbox.min.js"))),format.raw/*39.84*/(""""></script>
		<script src=""""),_display_(Seq[Any](/*40.17*/routes/*40.23*/.Assets.at("javascripts/jqm-datebox.mode.calbox.min.js"))),format.raw/*40.79*/(""""></script>

	</head>
    <body>
        """),_display_(Seq[Any](/*44.10*/content)),format.raw/*44.17*/("""
    </body>
</html>
"""))}
    }
    
    def render(title:String,content:Html): play.api.templates.HtmlFormat.Appendable = apply(title)(content)
    
    def f:((String) => (Html) => play.api.templates.HtmlFormat.Appendable) = (title) => (content) => apply(title)(content)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Sat Dec 14 14:26:01 CET 2013
                    SOURCE: /Users/hannibal/Repositories/CAS/Implementation/GUI/cas-web/app/org/haw/cas/web/views/main.scala.html
                    HASH: 9dd5581616c7f7977aab75b69168ab9aff97aa1b
                    MATRIX: 576->1|700->31|788->84|814->89|1284->523|1299->529|1351->559|1486->658|1501->664|1589->729|1706->810|1721->816|1779->852|1969->1006|1984->1012|2059->1065|2190->1160|2205->1166|2273->1212|2365->1268|2380->1274|2473->1345|2655->1491|2670->1497|2753->1558|2817->1586|2832->1592|2910->1648|2988->1690|3017->1697
                    LINES: 19->1|22->1|28->7|28->7|40->19|40->19|40->19|43->22|43->22|43->22|46->25|46->25|46->25|50->29|50->29|50->29|53->32|53->32|53->32|56->35|56->35|56->35|60->39|60->39|60->39|61->40|61->40|61->40|65->44|65->44
                    -- GENERATED --
                */
            