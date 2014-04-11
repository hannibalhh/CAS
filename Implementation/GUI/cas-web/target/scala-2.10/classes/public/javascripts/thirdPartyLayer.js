//Layer Wheater
$(function() {
    // var weatherLayer;
    $('#weather_box').click(function() {
        if ($(this).is(':checked')) {
            if (!map.weatherLayer) {
                weatherLayer = new google.maps.weather.WeatherLayer({
                    temperatureUnits: google.maps.weather.TemperatureUnit.CELSIUS,
                    windSpeedUnits: google.maps.weather.WindSpeedUnit.KILOMETERS_PER_HOUR
                });
            }
            weatherLayer.setMap(map);
            cloudLayer = new google.maps.weather.CloudLayer();
            cloudLayer.setMap(map);
        } else {
            weatherLayer.setMap(null);
            cloudLayer.setMap(null);
        }
    });
});

//Clouds Transit
$(function() {
    // var cloudLayer;
    $('#clouds_box').click(function() {
        if ($(this).is(':checked')) {
            if (!map.cloudLayer) {
                cloudLayer = new google.maps.weather.CloudLayer();
            }
            cloudLayer.setMap(map);
        } else {
            cloudLayer.setMap(null);
        }
    });
});

//Layer Transit
$(function() {
    //var transitLayer;
    $('#transit_box').click(function() {
        if ($(this).is(':checked')) {
            if (!map.transitLayer) {
                transitLayer = new google.maps.TransitLayer();
            }
            transitLayer.setMap(map);
        } else {
            transitLayer.setMap(null);
        }
    });
});

//Layer Traffic
$(function() {
    //var trafficLayer;
    $('#traffic_box').click(function() {
        if ($(this).is(':checked')) {
            if (!map.trafficLayer) {
                trafficLayer = new google.maps.TrafficLayer();
            }
            trafficLayer.setMap(map);
        } else {
            trafficLayer.setMap(null);
        }
    });
});

//Layer Precipitation
$(function() {
    var precipitation = false;
    $('#precipitation_box').click(function() {
        if ($(this).is(':checked')) {
            //if (precipitation === false) {
            var RainMap = new google.maps.ImageMapType({
                getTileUrl: function(coord, zoom) {
                    return "http://tile.openweathermap.org/map/rain/" + zoom + "/" + coord.x + "/" + coord.y + ".png";
                },
                tileSize: new google.maps.Size(256, 256),
                isPng: true,
                alt: "RainMap",
                name: "RainMap",
                maxZoom: 11
            });
            map.overlayMapTypes.push(RainMap);
            precipitation = true;
        } else {
            map.overlayMapTypes.clear();
        }
    });
});

//Layer Heatmap
$(function() {
    $('#heatbox_box').click(function() {
        // if ($(this).is(':checked')) {
            
        //     heatmapData = new google.maps.MVCArray(callsForService);
        //     var heatmap = new google.maps.visualization.HeatmapLayer({
        //         data: heatmapData,
        //         dissipating: false
        //     });

        //     heatmap.setMap(map);
        // } else {
        //     heatmap.setMap(null);
        // }
    });
});



//Layer Transit
$(function() {
    $("#layerReset").click(function() {

        //State all checkboxes set false at the beginning

        if ($('#precipitation_box').is(':checked')) {
            map.overlayMapTypes.clear();
        }

        if ($('#transit_box').is(':checked')) {
            transitLayer.setMap(null);
        }

        if ($('#traffic_box').is(':checked')) {
            trafficLayer.setMap(null);
        }

        if ($('#weather_box').is(':checked')) {
            weatherLayer.setMap(null);
            cloudLayer.setMap(null);
        }

        // if ($('#clouds_box').is(':checked')) {
        //     cloudLayer.setMap(null);
        // }

        // if ($('#heatbox_box').is(':checked')) {
        //     heatmap.setMap(null);
        // }
        $("input[type='checkbox']").attr("checked", false).checkboxradio("refresh");
        google.maps.event.trigger(map, "resize");   
    });
});
