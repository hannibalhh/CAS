   mapMarkerNumberNeeds = {
        Food: ko.observable('0'),
        Medicament: ko.observable('0'),
        Drink: ko.observable('0'),
        Accomodation: ko.observable('0'),
        Helper: ko.observable('0'),
        crevasses: ko.observable('0'),
        Userpositions: ko.observable('0')
    }
        var userPositions = [];
$(document).on('pageshow', '#karte', function(e, data) {
$("#listeViewModel").swipeleft(function(e){
        if($('#listleft').is(':visible')) {
            $("#listleft").hide();
            google.maps.event.trigger(map, "resize");
        }

});

$("#karte").swiperight(function(e){
        if($('#listleft').is(':visible')) {
            //do nothing
        } else {
            $("#listleft").show();
            google.maps.event.trigger(map, "resize");
        }
});

        $("#markerHelp").click(function() {
            $("#popupDialogHelp").popup("reposition", {positionTo: 'window'});
        });

        $("#layerHelp").click(function() {
            $("#popupDialogHelp").popup("reposition", {positionTo: 'window'});
        });

        $("#eventHelp").click(function() {
            $("#popupDialogHelp").popup("reposition", {positionTo: 'window'});
        });

        //Remove the overlays from the map
        function removeOverlays(markersArray) {
            if (markersArray.length > 0) {
                for (i in markersArray) {
                    markersArray[i].setMap(null);
                }
            }
             //markersArray = [];
        }
        
        // Removes the overlays from the map, but keeps them in the array
        function clearOverlays(markersArray) {
            if (markersArray) {
                for (i in markersArray) {
                    markersArray[i].setVisible(false);
                }
            }
        }

        function showOverlays(markersArray) {
            if (markersArray) {
                for (i in markersArray) {
                    markersArray[i].setVisible(true);
                }
            }
        }

        $('#listleftMarker').on('click', '.need', function() {
            var currentId = $(this).attr('id');
            if (needsArray[currentId][0].getVisible() === true) {
                console.log("true");
                clearOverlays(needsArray[currentId]);
                $("#"+currentId+"").css("background-color", "white");
            } else {
                showOverlays(needsArray[currentId]);
                $("#"+currentId+"").css("background-color", "#333333"); 
                //$("#crevasseChecker").click(function () { $(this).css("background-color", "black"); });
            }
        });

        var WS = window['MozWebSocket'] ? MozWebSocket : WebSocket;
        var loc = window.location,
            new_uri;
        if (loc.protocol === "https:") {
            new_uri = "wss:";
        } else {
            new_uri = "ws:";
        }
        new_uri += "//" + loc.host;
        new_uri += loc.pathname + "needs/socket";
        var needSocket = new WS(new_uri);
        //ws://localhost:9000/crevasses/socket
        needSocket.onopen = function(evt) {
            needSocket.send("{}");
        };

        needSocket.onmessage = function(event) {
            var data = JSON.parse(event.data);

            setMarker(data);
        };

                // if errors on websocket
        var onalert = function(event) {
            console.log("WebSocket closed");
        };

        needSocket.onerror = onalert;
        needSocket.onclose = onalert;

    // used when clicking on marker
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

    // Map with images for the Markers
    var iconMap = new Object();
    iconMap["Accomodation"] = "assets/images/marker/housing.png";
    iconMap["Medicament"] = "assets/images/marker/firstaid.png";
    iconMap["Helper"] = "assets/images/marker/helper.png";
    iconMap["Food"] = "assets/images/marker/steak.png";
    iconMap["Drink"] = "assets/images/marker/coffee.png";
    iconMap["crevasse"] = "assets/images/marker/caution.png";
    iconMap["Userpositions"] = "assets/images/marker/male.png";
    
    function getImage(k) {
        return iconMap[k];
    }

    //Marker Array
    accomodation =[];
    medicament = [];
    helper = [];
    food = [];
    drink = [];
    //positions = [];

    var needsArray = new Object();
    needsArray["Accomodation"] = accomodation;
    needsArray["Medicament"] = medicament;
    needsArray["Helper"] = helper;
    needsArray["Food"] = food;
    needsArray["Drink"] = drink;
    //needsArray["Userpositions"] = userPosition;

    function getArray(k) {
        return needsArray[k];
    }

    // Add a element to the list of markers
    function addListElement(pinIcon, type) {
        var contains = $.inArray(type, listleftMarkerArray);
        if (contains == -1) {
            listleftMarkerArray.push(type);
            $('<li><a><img src=' + pinIcon + ' class="ui-li-icon ui-corner-none">' + type + '<span class="ui-li-count" data-bind="text: '+type+'"</a></a><a id='+type+' class="need"></a></li>').appendTo('#listleftMarker');
            //refresh list
            ko.applyBindings(mapMarkerNumberNeeds);
            $('#listleftMarker').listview("refresh");
        }
    }
    //Delete Element in Markerlist
    //ToDo
    function deleteElement(type){
        var contains = $.inArray(type, listleftMarkerArray);
        if (contains == 0) {
 
        }
    }

    listleftMarkerArray = [];
    arrayMarker = [];
    function setMarker(data) {
        for (k in needsArray) {
             for (i = 0; i < needsArray[k].length; i++) {
                    removeOverlays(needsArray[k]);
                    needsArray[k] = [];
             };
             //ToDo Löschen des Elementes aus der Liste
        };
        //Clear List
       // $('#listleftMarker').empty();
       
        for (i = 0; i < data.needs.length; i++) {
            if (data.needs[i] !== null) {
                var type = data.needs[i].type;
                var author = data.needs[i].author;
                var img = data.needs[i].pictureUrl;
                var tweet = "@" + author + ": " + data.needs[i].message;
                var handle = "http://www.twitter.com/" + author;
                var dt = "<br><br><right><small>" + data.needs[i].creationTime + "</small></right>";
                var wLogo = "<a href='" + handle + "' target='_blank'><img class='avi' src=" + img + "></img></a> <a href='" + handle + "' target='_blank'>@" + author + "</a>: " + data.needs[i].message + dt;

                //
                var infowindow = new google.maps.InfoWindow({
                    content: tweet
                });

                var pinIcon = new google.maps.MarkerImage(
                    img,
                    null, /* size is determined at runtime */
                    null, /* origin is 0,0 */
                    null, /* anchor is bottom center of the scaled image */
                    new google.maps.Size(52, 52)
                );

                pinIcon = getImage(type);

                addListElement(pinIcon, type);

                var marker = new google.maps.Marker({
                    map: map,
                    icon: pinIcon,
                    title: tweet,
                    position: new google.maps.LatLng(data.needs[i].geo.coordinates[0], data.needs[i].geo.coordinates[1])
                });

                arrayMarker = getArray(type);
                arrayMarker.push(marker);
                var number = needsArray[type].length;
                mapMarkerNumberNeeds[type](number);
                // marker.setAnimation(google.maps.Animation.DROP);
                infowindow = new google.maps.InfoWindow;
                bindInfoW(marker, wLogo, infowindow);

            }
        };
    }
      var positions = [];
     $("#userPosition").click(function() {
        $("#since1").css("z-index", "1200");
        var radius = $("#radius").val();
        var since1 = $("#since1").datebox('getTheDate');
        var sinceDate = new Date(since1.getFullYear(), since1.getMonth(), since1.getDate());
        var maplatlng =  map.getCenter();
        var latMapPosition = maplatlng.lat();
        var longMapPosition = maplatlng.lng();
        // Gibt einen Zeitstempel aus
        var since = sinceDate.getTime();
        function userPosition(positions) {
            var urlSearch = '/userpositions?la='+latMapPosition+'&lo='+longMapPosition+'&oldest='+since+'&radius='+radius;
            $.ajax({
                type: "GET",
                beforeSend: function() {
                    $.mobile.loading('show');
                }, //Show spinner
                complete: function() {
                    $.mobile.loading('hide');
                }, //Hide spinner
                url: urlSearch,
                //data: form_data,
                //timeout: 100000,
                statusCode: {
                    404: function() {
                        alert("page not found");
                    }
                },
                success: function(data) {
                    // Aus den Daten die Marker generieren
                    setUser(data);
                },
                error: function(xhr, ajaxOptions, thrownError) {
                    $("#userPositionDialog").popup("close");
                    console.log(xhr.status);
                    console.log(thrownError);
                }
            });
        }

    // Add a element to the list of markers
    function addListElementPosition(pinIcon, type) {
        var contains = $.inArray(type, listleftMarkerArray);
        if (contains == -1) {
            listleftMarkerArray.push(type);
            $('<li><a><img src=' + pinIcon + ' class="ui-li-icon ui-corner-none">' + type + '<span class="ui-li-count" data-bind="text: '+type+'"</a></a><a id='+type+' class="position"></a></li>').appendTo('#listleftMarker');
            //refresh list
            ko.applyBindings(mapMarkerNumberNeeds);
            $('#listleftMarker').listview("refresh");
        }
    }

    $('#listleftMarker').on('click', '.position', function() {
            var currentId = $(this).attr('id');
            if (userPositions[0].getVisible() === true) {
                console.log("true");
                clearOverlays(userPositions);
                $("#"+currentId+"").css("background-color", "white");
            } else {
                showOverlays(userPositions);
                $("#"+currentId+"").css("background-color", "#333333"); 
            }
        });

        function setUser(data) {
        //Delete all Userpositionmarker
        if (userPositions.length > 0) {
                for (i in userPositions) {
                    userPositions[i].setMap(null);
                }
            }
            userPositions = [];
            var user = data;
            //Array,Object
            var positions = user.userpositions;
            //JSON
            var d = JSON.parse(JSON.stringify(positions));
            for (i = 0; i < positions.length; i++) {
                var type = positions[i].type;
                var author = positions[i].author;
                var tweet = "@" + author + ": " + positions[i].message;
                var handle = "http://www.twitter.com/" + author;
                var dt = "<br><br><right><small>" + positions[i].creationTime + "</small></right>";
                var wLogo = "<a href='" + handle + "' target='_blank'></a> <a href='" + handle + "' target='_blank'>@" + author + "</a>: " + positions[i].message + dt;
                var infowindow = new google.maps.InfoWindow({
                    content: tweet
                });
                var img = "assets/images/marker/male.png";

                var pinIcon = new google.maps.MarkerImage(
                    img,
                    null, /* size is determined at runtime */
                    null, /* origin is 0,0 */
                    null, /* anchor is bottom center of the scaled image */
                    new google.maps.Size(52, 52)
                );


                pinIcon = getImage(type);

                addListElementPosition(pinIcon, type);

                var lat = positions[i].geo.coordinates[0];
                var lng = positions[i].geo.coordinates[1];
                var marker = new google.maps.Marker({
                    map: map,
                    icon: pinIcon,
                    title: tweet,
                    position: new google.maps.LatLng(lat, lng)
                });

                google.maps.event.addListener(marker, 'rightclick', function(event) {
                    marker.setVisible(false);
                });

                //arrayMarker = getArray(type);
                userPositions.push(marker);
                var number = userPositions.length;
                mapMarkerNumberNeeds[type](number);
                $("#userPositionDialog").popup("close");
                marker.setAnimation(google.maps.Animation.DROP);
                infowindow = new google.maps.InfoWindow;
                bindInfoW(marker, wLogo, infowindow);
            };
        };


        userPosition();

    });

        initialize();
});
