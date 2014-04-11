//Script for the Needmessage Table for the Dashboard
$(document).on('pageinit', '#dashboard', function(e, data) {
  // var author = {
  //       author: ko.observable('@author')
  //   }
  //   ko.applyBindings(author);
$("#messages").click(function() {
	$("#panel").hide();
    $("#node").hide();

    $("#table").show();
    $("#labelPosition").show();
    // $("#positionMessage").show();
    $("#formSearch").show();
    $("#positionMessage").parent().show();

    //Remove all Rows
    $("#table > tbody").html("");
    // $("#messageTable").remove();

    //$("<label id="+"labelPosition"+">Positionsetting:</label>").appendTo("#box");
    // $("<select data-theme="+"b"+" data-native-menu="+"false" +"name="+"select-choice-b"+" id="+"positionMessage"+"><option value="+"Both"+">Both</option><option value="+"With"+">Only position</option><option value="+"Without"+">Without position</option></select>").appendTo("#box");               
    //$( "#positionMessage" ).buttonMarkup( "refresh" );
    //Create Table
    // $("<table data-role="+"table"+ " data-mode="+"columntoggle"+" class="+"ui-body-f ui-shadow table-stripe ui-responsive"+" data-column-btn-theme="+"b"+" data-column-btn-text="+"Columns to display..."+" data-column-popup-theme="+"a"+" id="+"messageTable"+">").appendTo("#box");
    // $("<table data-role="+"table"+ " data-mode="+"table"+" class="+"ui-body-f ui-shadow table-stripe ui-responsive id="+"messageTable"+"></table>").appendTo("#box");
    // $("<thead><tr class="+"ui-bar-f"+"><th data-priority="+"6"+">Number</th><th data-priority="+"1"+">Author</th> <th data-priority="+"5"+">Typ</th> <th data-priority="+"2"+">Message</th><th data-priority="+"3"+">Provenance</th><th data-priority="+"4"+">CreationTime</th></tr></thead>").appendTo("#messageTable");
	// $("<tbody></tbody>").appendTo("#messageTable");

    //Open PopUp to communicate with the author
    $(".authorMessage").click(function() {
        console.log("true");
        var href = $(this).html();
        author.author(href);
    });

    function tableNeedData() {
        var urlSearch = '../needs';
        $.ajax({
            type: "GET",
            // beforeSend: function() {
            //     $.mobile.loading('show');
            // }, //Show spinner
            // complete: function() {
            //     $.mobile.loading('hide');
            // }, //Hide spinner
            url: urlSearch,
            //data: form_data,
            //timeout: 100000,
            statusCode: {
                404: function() {
                    alert("page not found");
                }
            },
            success: function(data) {
                setRow(data);
            },
            error: function(xhr, ajaxOptions, thrownError) {
                console.log(xhr.status);
                console.log(thrownError);
            }
        });
    }

    function tableCrevasseData() {
        var urlSearch = '../crevasses';
        $.ajax({
            type: "GET",
            // beforeSend: function() {
            //     $.mobile.loading('show');
            // }, //Show spinner
            // complete: function() {
            //     $.mobile.loading('hide');
            // }, //Hide spinner
            url: urlSearch,
            //data: form_data,
            //timeout: 100000,
            statusCode: {
                404: function() {
                    alert("page not found");
                }
            },
            success: function(data) {
                setRowCrevasse(data);
            },
            error: function(xhr, ajaxOptions, thrownError) {
                console.log(xhr.status);
                console.log(thrownError);
            }
        });
    }

    function tablePostData() {
        var urlSearch = '../posts';
        $.ajax({
            type: "GET",
            // beforeSend: function() {
            //     $.mobile.loading('show');
            // }, //Show spinner
            // complete: function() {
            //     $.mobile.loading('hide');
            // }, //Hide spinner
            url: urlSearch,
            //data: form_data,
            //timeout: 100000,
            statusCode: {
                404: function() {
                    alert("page not found");
                }
            },
            success: function(data) {
                setRowPost(data);
            },
            error: function(xhr, ajaxOptions, thrownError) {
                console.log(xhr.status);
                console.log(thrownError);
            }
        });
    }

    function setRow(data){
    var table = document.getElementById('table');
    for (i = 0; i < data.needs.length; i++){
                var rowsInTable = table.rows.length;
                    $("#table tbody").append($("<tr>\n")
                    .append("<th>" + (rowsInTable) + "</th>\n")
                    .append("<td><a class="+"authorMessage"+" data-rel="+"popup"+" href="+"#massageSend"+ ">"+(data.needs[i].author)+"</a></td>\n")
                    .append("<th>" + (data.needs[i].type) + "</th>\n")
                    .append("<td>"+(data.needs[i].message) + "</td>\n")
                    .append("<td>"+(data.needs[i].provenance) + "</td>\n")
                    .append("<td>"+(data.needs[i].creationTime) + "</td>\n")
                    .append("</tr>"));
        };
    }

    function setRowCrevasse(data){
    var table = document.getElementById('table');
    for (i = 0; i < data.crevasses.length; i++){
                var rowsInTable = table.rows.length;
                    $("#table tbody").append($("<tr>\n")
                    .append("<th>" + (rowsInTable) + "</th>\n")
                    .append("<td><a class="+"authorMessage"+" data-rel="+"popup"+" href="+"#massageSend"+ ">"+(data.crevasses[i].author)+"</a></td>\n")
                    .append("<th>" + (data.crevasses[i].type) + "</th>\n")
                    .append("<td>"+(data.crevasses[i].message) + "</td>\n")
                    .append("<td>"+(data.crevasses[i].provenance) + "</td>\n")
                    .append("<td>"+(data.crevasses[i].creationTime) + "</td>\n")
                    .append("</tr>"));
        };
    }

    function setRowPost(data){
    var table = document.getElementById('table');
    for (i = 0; i < data.Posts.length; i++){
        var rowsInTable = table.rows.length;
                    $("#table tbody").append($("<tr>\n")
                    .append("<th>" + (rowsInTable) + "</th>\n")
                    .append("<td><a class="+"authorMessage"+" data-rel="+"popup"+" href="+"#massageSend"+ ">"+(data.Posts[i].author)+"</a></td>\n")
                    .append("<th>" + (data.Posts[i].type) + "</th>\n")
                    .append("<td>"+(data.Posts[i].message) + "</td>\n")
                    .append("<td>"+(data.Posts[i].provenance) + "</td>\n")
                    .append("<td>"+(data.Posts[i].creationTime) + "</td>\n")
                    .append("</tr>"));
        };
    }

    // function sortTable(){
    //     var $sort = this;
    //     var $table = $('#messageTable');
    //     var $rows = $('tbody > tr',$table);
    //     $rows.sort(function(a, b){
    //         var keyA = $('td',a).text();
    //         var keyB = $('td',b).text();
    //         if($($sort).hasClass('asc')){
    //             return (keyA > keyB) ? 1 : 0;
    //         } else {
    //             return (keyA > keyB) ? 1 : 0;
    //         }
    //     });
    //     $.each($rows, function(index, row){
    //       $table.append(row);
    //     });
    // }

  $("#positionMessage").change(function() {
            var position = $("#positionMessage").val()
            if (position == "With") {
                //$("#messageTable").empty();
                $("#table > tbody").html("");
                tableNeedData();
                tableCrevasseData() 
            } else if (position == "Without") {
                $("#table > tbody").html("");
                tablePostData();
            } else {
               $("#table > tbody").html("");
               tablePostData();
               tableNeedData();
               tableCrevasseData() 
            };

    });

  tablePostData();
  tableNeedData();
  tableCrevasseData() 

   $("#messageTable").table().table("refresh");

});
});