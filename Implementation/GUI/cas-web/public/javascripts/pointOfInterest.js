
var placeOfInterest = [];

    //Remove the overlays from the map
    function removeOverlays() {
        if (placeOfInterest ) {
            for (i in placeOfInterest) {
                placeOfInterest[i].setMap(null);
            }
        }
        placeOfInterest = [];
        google.maps.event.trigger(map, "resize");
    }
$("#interest").change(function() {
    

    removeOverlays(placeOfInterest);
    var requestOption = $(this).val();
    var pyrmont = new google.maps.LatLng(53.571307, 10.052077);
    var maplatlng =  map.getCenter();
    var request = {
        location: maplatlng,
        radius: 500000,
        types: [requestOption]
    };
    infowindow = new google.maps.InfoWindow();
    var service = new google.maps.places.PlacesService(map);
    service.nearbySearch(request, callback);

    function callback(results, status) {
        if (status == google.maps.places.PlacesServiceStatus.OK) {
            for (var i = 0; i < results.length; i++) {
                var result = results[i];
                createMarker(result);
            }
        }
    }
    var img = "assets/images/marker/sight.png";
    function createMarker(place) {
        var placeLoc = place.geometry.location;
        var marker = new google.maps.Marker({
            map: map,
            icon: img,
            position: place.geometry.location
        });

        placeOfInterest.push(marker);

        function getDetails(place) {
            var request = {
                reference: place.reference
            };

            service.getDetails(request, function(place, status) {
                if (status == google.maps.places.PlacesServiceStatus.OK) {
                    var contentStr = '<h5>' + place.name + '</h5><p>' + place.formatted_address;
                    if ( !! place.formatted_phone_number) contentStr += '<br>' + place.formatted_phone_number;
                    if ( !! place.website) contentStr += '<br><a target="_blank" href="' + place.website + '">' + place.website + '</a>';
                    contentStr += '<br>' + place.types + '</p>';
                    infowindow.setContent(contentStr);
                    infowindow.open(map, marker);
                } else {
                    var contentStr = "<h5>No Result, status=" + status + "</h5>";
                    infowindow.setContent(contentStr);
                    infowindow.open(map, marker);
                }
            });

        }


        google.maps.event.addListener(marker, 'click', function() {
            getDetails(place);
        });
    }

});
