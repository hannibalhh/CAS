$(document).on('pageshow', '#dashboard', function(e, data) {

    $("#timeLineHelp").click(function() {
            $("#dashboardDialogHelp").popup("reposition", {positionTo: 'window'});
        });

    $("#messagesHelp").click(function() {
            $("#dashboardDialogHelp").popup("reposition", {positionTo: 'window'});
        });

    // $('#typeChart').val("Line").selectmenu("refresh");
    // // $('#lang').val(aval).selectmenu("refresh");
    // // $('#lang').val(aval).selectmenu("refresh");
    var myselectTypeChart = $("select#typeChart");
    myselectTypeChart[0].selectedIndex = 5;
    myselectTypeChart.selectmenu("refresh");
    var myselectChoice = $("select#typeChoice");
    myselectChoice[0].selectedIndex = 1;
    myselectChoice.selectmenu("refresh");
    var myselectPosition = $("select#position1");
    myselectPosition[0].selectedIndex = 1;
    myselectPosition.selectmenu("refresh");

    var day = 86400;
    var days14 = 86400*14;
    $('#time1').trigger('datebox', {'method':'dooffset', 'type':'s', 'amount':days14}).trigger('datebox', {'method':'doset'});

    $("#table").hide();
    $("#labelPosition").hide();
    $("#formSearch").hide();
    $("#positionMessage").parent().hide();

        $("#layerHelpDash").click(function() {
            $("#markerDialogHelp").popup("reposition", {positionTo: 'window'});
        });
        $("#eventHelpDash").click(function() {
            $("#markerDialogHelp").popup("reposition", {positionTo: 'window'});
        });

        $(".ui-table-columntoggle-btn").css("display","none !important");

    // Aublenden von der Message-Table
    $("#needs").click(function() {

        $("#table").hide();
        $("#labelPosition").hide();
        $("#formSearch").hide();
        $("#positionMessage").parent().hide();

        $("#panel").show();
        $("#node").show();

    });

    function timeChart() {
            // Type
            var typ = $("#typeChoice").val();
            // Time
            var time1 = $("#time1").datebox('getTheDate');
            var date1 = new Date(time1.getFullYear(), time1.getMonth(), time1.getDate());
            // Gibt einen Zeitstempel aus
            var zeitInMillisekunden = new Date().getTime();
            var from = (2*zeitInMillisekunden) - date1.getTime();
            var to = zeitInMillisekunden;
            //var to = 1386079437987;
            // Position
            var position = $("#position1").val();
            // console.log("typ:" + typ + "from" + from);
            var urlSearch = '/timelines';
            var form_data = {
                ts: typ,
                from: from,
                to: to,
                ps: position
            };
            $.ajax({
                type: "GET",
                beforeSend: function() {
                    $.mobile.loading('show');
                }, //Show spinner
                complete: function() {
                    $.mobile.loading('hide');
                }, //Hide spinner
                dataType: 'json',
                url: urlSearch,
                data: form_data,
                timeout: 100000,
                statusCode: {
                    404: function() {
                        alert("page not found");
                    }
                },
                success: function(data) {
                    drawChart(data);
                },
                error: function(xhr, ajaxOptions, thrownError) {
                    console.log(xhr.status);
                    console.log(thrownError);
                    $('#anzeigen').prop('disabled', false);
                    $("#timeOutPopup").popup( "open" )
                }
            });
        }

        function drawTimeline(points) {
            var data = google.visualization.arrayToDataTable(points);

            var firstdate = points[1][0];
            var lastdate = points[points.length - 1][0];
            var options = {
                title: 'Messaging history [' + firstdate + " to " + lastdate + "]",
                hAxis: {
                    title: "Date"
                },
                vAxis: {
                    title: "Number"
                }
            };
            var chart;
            var charttype = $("#typeChart").val();
            if (charttype == "Chart Type") {
                alert("Please set a Chart Type.");
                $('#anzeigen').prop('disabled', false);
                return false;
            };
            if (charttype == "bar") {
                chart = new google.visualization.BarChart(document.getElementById('node'));
            } else if (charttype == "column") {
                chart = new google.visualization.ColumnChart(document.getElementById('node'));
            } else if (charttype == "area") {
               chart = new google.visualization.AreaChart(document.getElementById('node'));
            } else if (charttype == "stepped") {
                chart = new google.visualization.SteppedAreaChart(document.getElementById('node'));
            } else if(charttype == "combo") {
                chart = new google.visualization.ComboChart(document.getElementById('node'));
            } else {
                chart = new google.visualization.LineChart(document.getElementById('node'));
            };

            chart.draw(data, options);
            $('#anzeigen').prop('disabled', false);
        }


        function drawChart(dat) {
            var pointsData = dat;
            //Array,Object
            var points = pointsData.timelines;
            //JSON
            var d = JSON.parse(JSON.stringify(points));

            drawTimeline(d);
        };
    $('#anzeigen').prop('disabled', true);
    timeChart();
    $("#anzeigen").click(function() {

        $('#anzeigen').prop('disabled', true);
        // var time = $("#time1").val();
        // var typeCh = $("#typeChart").val();
        // var typCh = $("#typeChoice").val();
        // var positionsetting = $("#position1").val();
        // if(time == ""){
        //     alert("Please set a Date.");
        //     $('#anzeigen').prop('disabled', false);
        //     return false;
        // }
        // if(typeCh == "Chart Type"){
        //     alert("Please set a Chart Type.");
        //     $('#anzeigen').prop('disabled', false);
        //     return false;
        // }
        // if(typCh == "Message Type"){
        //     alert("Please set a Message Type.");
        //     $('#anzeigen').prop('disabled', false);
        //     return false;
        // }
        // if(positionsetting == "Positionsetting"){
        //     alert("Please set a Positionsetting.");
        //     $('#anzeigen').prop('disabled', false);
        //     return false;
        // }

        // Set a callback to run when the Google Visualization API is loaded.
        google.setOnLoadCallback(drawChart);

        timeChart();

    });
});
