// $(document).delegate('#karte', 'pageinit', function() {
//     var height = ($(window).height() - $("#karte").find('[data-role="header"]').outerHeight() - $("#karte").find('[data-role="footer"]').outerHeight());
//     $("#map_canvas").height(height);
// });

function bindInfoW(marker, contentString, infowindow) {
    google.maps.event.addListener(marker, 'click', function() {
        infowindow.setContent(contentString);
        infowindow.open(map, marker);
    });
}

// Enable the visual refresh
google.maps.visualRefresh = true;
var minZoomLevel = 7;
// Mit dieser Methode wird die Karte erstellt und die tweets Methode aufgerufen
var map;
function initialize() {
     var height = ($(window).height() - $("#karte").find('[data-role="header"]').outerHeight() - 20 - $("#karte").find('[data-role="footer"]').outerHeight());
    $("#map_canvas").height(height);

    /*
        Build list of map types.
        You can also use var mapTypeIds = ["roadmap", "satellite", "hybrid", "terrain", "OSM"]
        but static lists sucks when google updates the default list of map types.
    */
    var mapTypeIds = [];
    for (var type in google.maps.MapTypeId) {
        mapTypeIds.push(google.maps.MapTypeId[type]);
    }
    //mapTypeIds.push(google.maps.MapTypeId.ROADMAP);
    mapTypeIds.push("OSM");
    //add tile
        

    map = new google.maps.Map(document.getElementById('map_canvas'), {
        zoom: minZoomLevel,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        mapTypeControlOptions: {
            mapTypeIds: mapTypeIds
            //style: google.maps.MapTypeControlStyle.DROPDOWN_MENU
        }
    });
    var defaultLatLng = new google.maps.LatLng(53.555989, 10.022829); // Default to Berliner Tor if no geolocation
    // Try HTML5 geolocation
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            //localStorage.pos = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
            //var pos = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
            var pos = defaultLatLng;
            map.setCenter(pos);
            setMarkerAndOtherMapType(pos);
           
        }, function() {
            handleNoGeolocation(true);
        });
    } else {
        // Browser doesn't support Geolocation
        handleNoGeolocation(false);
        // map.setCenter(defaultLatLng); // show Default Map
    }

  function handleNoGeolocation(errorFlag) {
    if (errorFlag == true) {
      // alert("Geolocation service failed.");
      $("#geolocationFailedPopup").popup( "open" )
      initialLocation = defaultLatLng;
      setMarkerAndOtherMapType(defaultLatLng);
    } else {
      // alert("Your browser doesn't support geolocation. We've placed you in Hamburg Berliner Tor.");
      $("#geolocationFailedPopup").popup( "open" )
      initialLocation = defaultLatLng;
      setMarkerAndOtherMapType(defaultLatLng);
    }
    map.setCenter(initialLocation);
  }

  function setMarkerAndOtherMapType(pos){
     marker = new google.maps.Marker({
                map: map,
                title: "Du bist hier!",
                position: pos
            });

            var infowindow = new google.maps.InfoWindow;

            bindInfoW(marker,"<div>Du bist hier !</div>", infowindow);

            map.mapTypes.set("OSM", new google.maps.ImageMapType({
                getTileUrl: function(coord, zoom) {
                    return "http://tile.openstreetmap.org/" + zoom + "/" + coord.x + "/" + coord.y + ".png";
                },
                tileSize: new google.maps.Size(256, 256),
                name: "OpenStreetMap",
                maxZoom: 18
            }));
            //State all checkboxes set false at the beginning
            $("input[type='checkbox']").attr("checked",false).checkboxradio("refresh");
  }

}

