
var markersArray = [];
markerArrayCrevasse = [];


//   $( document ).ready(function() {
//   $("#crevasseChecker").click(function () { $(this).css("background-color", "green"); });
// });
// Add a element to the list of markers

function addListElement(pinIcon, type) {
    var contains = $.inArray(type, listleftMarkerArray);
    // if (sessionStorage.getItem(type) == null){
    if (contains == -1) {
        listleftMarkerArray.push(type);
        //sessionStorage.setItem(type, "not Null");
        $('<li><a><img src=' + pinIcon + ' class="ui-li-icon ui-corner-none">' + type + '<span class="ui-li-count" data-bind="text: crevasses"></span></a><a id="crevasseChecker"></a></li>').appendTo('#listleftMarker');
        $("#crevasseChecker").click(function() {
            $(this).css("background-color", "white");
        });
        var crevasses = markersArray.length;
        ko.applyBindings(mapMarkerNumberNeeds);
        mapMarkerNumberNeeds.crevasses(crevasses);
        $('#listleftMarker').listview("refresh");
    }
}
$(document).on('pageshow', '#karte', function(e, data) {

    function bindInfoW(marker, contentString, infowindow) {
        google.maps.event.addListener(marker, 'click', function() {
            var timeline = [];
            infowindow.setContent(contentString);
            infowindow.open(map, marker);
        });

        google.maps.event.addListener(marker, 'rightclick', function(event) {
            marker.setVisible(false);
            infowindow.close();
        });

        google.maps.event.addListener(marker, 'dblclick', function(event) {
            marker.setVisible(false);
            infowindow.close();
        });
    }

    $('#listleftMarker').on('click', '#crevasseChecker', function() {
        if (markersArray[0].getVisible() === true) {
            clearOverlays();
        } else {
            showOverlays();
            $("#crevasseChecker").css("background-color", "#333333");
        }
    });

    //Remove the overlays from the map

    function removeOverlays() {
        if (markersArray.length > 0) {
            for (i in markersArray) {
                markersArray[i].setMap(null);
            }
        }
        // markersArray = [];
    }

    // Removes the overlays from the map, but keeps them in the array

    function clearOverlays() {
        if (markersArray) {
            for (i in markersArray) {
                markersArray[i].setVisible(false);
            }
        }
    }

    // Shows any overlays currently in the array

    function showOverlays() {
        if (markersArray) {
            for (i in markersArray) {
                markersArray[i].setVisible(true);
            }
        }
    }

    var WS = window['MozWebSocket'] ? MozWebSocket : WebSocket;
    var loc = window.location,
        new_uri;
    if (loc.protocol === "https:") {
        new_uri = "wss:";
    } else {
        new_uri = "ws:";
    }
    new_uri += "//" + loc.host;
    new_uri += loc.pathname + "crevasses/socket";
    var mapSocket = new WS(new_uri);
    //ws://localhost:9000/crevasses/socket
    mapSocket.onopen = function(evt) {
        mapSocket.send("{}");
    };
    markersArray = [];
    mapSocket.onmessage = function(event) {
        //markersArray = [];
        if (markersArray.length > 1) {
            //markersArray = [];
            //var crevasses = markersArray.length;
            //mapMarkerNumber.crevasses(crevasses);
            removeOverlays(markersArray);
            markersArray = [];
            console.log("leere crevasses");
        }

        console.log("onMessage recieved");
        var data = JSON.parse(event.data);
        for (i = 0; i < data.crevasses.length; i++) {
            var type = data.crevasses[i].type;
            var author = data.crevasses[i].author;
            var tweet = "@" + author + ": " + data.crevasses[i].message;
            var handle = "http://www.twitter.com/" + author;
            var dt = "<br><br><right><small>" + data.crevasses[i].creationTime + "</small></right>";
            var wLogo = "<a href='" + handle + "' target='_blank'></a> <a href='" + handle + "' target='_blank'>@" + author + "</a>: " + data.crevasses[i].message + dt;

            // marker = moveMaker(map, marker, data.longitude, data.latitude);
            var infowindow = new google.maps.InfoWindow({
                content: tweet
            });

            var img = "assets/images/marker/caution.png";
            //var img = getImage(type);
            var pinIcon = new google.maps.MarkerImage(
                img,
                null, /* size is determined at runtime */
                null, /* origin is 0,0 */
                null, /* anchor is bottom center of the scaled image */
                new google.maps.Size(42, 42)
            );

            var marker = new google.maps.Marker({
                map: map,
                icon: pinIcon,
                title: wLogo,
                position: new google.maps.LatLng(data.crevasses[i].geo.coordinates[0], data.crevasses[i].geo.coordinates[1])
            });


            markersArray.push(marker);
            var crevasses = markersArray.length;
            mapMarkerNumberNeeds.crevasses(crevasses);
            // marker.setAnimation(google.maps.Animation.DROP);
            bindInfoW(marker, wLogo, infowindow);
            addListElement(img, type);

        };
    };

    // if errors on websocket
    var onalert = function(event) {
        console.log("WebSocket closed");
    };

    mapSocket.onerror = onalert;
    mapSocket.onclose = onalert;

});
