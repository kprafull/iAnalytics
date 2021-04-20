/**
 * Function to display available reports in the designer
 */
function displayReports(data) {
    console.log(data);
    $('#analytics-reports a.report').remove();
    var i;
    for (i in data) {
        $("#analytics-reports").append('<a class="report" href="#" id="' + data[i].id + '">' + data[i].name + '</a>');
        $("#" + data[i].id).data("chart_attributes", JSON.parse(data[i].data));
    }
    $("#analytics-reports").app
}

/**
 * Load the saved reports and display
 */
function loadReports(doneFunction) {
    $.ajax({
        url: 'analytics/report'
    }).done(doneFunction).fail(function (jqXHR, textStatus, errorThrown) {
        console.log("failed textStatus=" + textStatus + " errorThrown=" + errorThrown);
        showError("Failed to load the reports. textStatus=" + textStatus + " errorThrown=" + errorThrown)
    });
}
