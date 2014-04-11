$(document).on('pageshow', '#karte', function(e, data) {

//     $("#userPosition").click(function() {

//         function userPosition() {
//             // var latMyPosition = pos.lat();
//             // var longMyPosition = pos.lng();
//             // var urlSearch = 'http://localhost:9000/geoinformationstandardwith?la='+latMyPosition+'&lo='+longMyPosition;
//             var urlSearch = '/userpositions?la=51&lo=9&oldest=0&radius=5000';
//             // var form_data = {
//             //     lat: latMyPosition,
//             //     long: longMyPosition,
//             //     is_ajax: 1
//             // };
//             $.ajax({
//                 type: "GET",
//                 // beforeSend: function() {
//                 //     $.mobile.loading('show');
//                 // }, //Show spinner
//                 // complete: function() {
//                 //     $.mobile.loading('hide');
//                 // }, //Hide spinner
//                 url: urlSearch,
//                 //data: form_data,
//                 //timeout: 100000,
//                 statusCode: {
//                     404: function() {
//                         alert("page not found");
//                     }
//                 },
//                 success: function(data) {
//                     // Aus den Daten die Marker generieren
//                     //setMarker(data);
//                     setUser(data);
//                 },
//                 error: function(xhr, ajaxOptions, thrownError) {
//                     console.log(xhr.status);
//                     console.log(thrownError);
//                 }
//             });
//         }

// // "userpositions": [
// //         {
// //             "type": "Userpositions",
// //             "id": "0",
// //             "geo": {
// //                 "coordinates": [
// //                     "10.087782",
// //                     "53.629982"
// //                 ]
// //             },
// //             "author": "autor",
// //             "message": "dumdidum",
// //             "creationTime": "01.01.1970 01:00",
// //             "provenance": "twitter",
// //             "radius": "4.0",
// //             "certainty": "5"
// //         },
// //         {
// //             "type": "Userpositions",
// //             "id": "1",
// //             "geo": {
// //                 "coordinates": [
// //                     "9.793333",
// //                     "53.530556"
// //                 ]
// //             },
// //             "author": "autor2",
// //             "message": "dumdidum2",
// //             "creationTime": "01.01.1970 01:00",
// //             "provenance": "twitter",
// //             "radius": "5.0",
// //             "certainty": "5"
// //         }
// //     ]
//         function setUser(data) {
//             var user = data;
//             //Array,Object
//             var positions = user.userpositions;
//             //JSON
//             var d = JSON.parse(JSON.stringify(positions));

//             for (i = 0; i < positions.length; i++) {
//                 console.log(positions[i].type);
//                 console.log(positions[i].geo.coordinates[0]);
//                 var type = positions[i].type;
//                 var author = positions[i].author;
//                 var tweet = "@" + author + ": " + positions[i].message;
//                 var handle = "http://www.twitter.com/" + author;
//                 var dt = "<br><br><right><small>" + positions[i].creationTime + "</small></right>";
//                 //var wLogo = "<a href='" + handle + "' target='_blank'><img class='avi' src=" + img + "></img></a> <a href='" + handle + "' target='_blank'>@" + author + "</a>: " + data.needs[i].message + dt;
//                 var wLogo = "<a href='" + handle + "' target='_blank'></a> <a href='" + handle + "' target='_blank'>@" + author + "</a>: " + positions[i].message + dt;
//                 var infowindow = new google.maps.InfoWindow({
//                     content: tweet
//                 });
//                 var img = "assets/images/marker/male.png";
//                 var pinIcon = new google.maps.MarkerImage(
//                     img,
//                     null, /* size is determined at runtime */
//                     null, /* origin is 0,0 */
//                     null, /* anchor is bottom center of the scaled image */
//                     new google.maps.Size(52, 52)
//                 );

//                 var lat = positions[i].geo.coordinates[0];
//                 var lng = positions[i].geo.coordinates[1];
//                 var marker = new google.maps.Marker({
//                     map: map,
//                     icon: pinIcon,
//                     title: tweet,
//                     position: new google.maps.LatLng(lat, lng)
//                 });

//                 google.maps.event.addListener(marker, 'rightclick', function(event) {
//                     marker.setVisible(false);
//                 });

//                 marker.setAnimation(google.maps.Animation.DROP);
//                 infowindow = new google.maps.InfoWindow;
//                 bindInfoW(marker, wLogo, infowindow);
//             };
//         };


//         userPosition();

//     });

});
