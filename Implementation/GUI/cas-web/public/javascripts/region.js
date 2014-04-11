$("#report").click(function() {

	// hide List
	$('#listleft').hide();

	// resize the map
	google.maps.event.trigger(map, "resize");

    //http://localhost:9000/assets/javascripts/hamburg.kml
    var ctaLayer = new google.maps.KmlLayer('http://fdlmobile.fd.funpic.de/hamburg.kml');
    ctaLayer.setMap(map);

});