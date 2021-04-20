/**
 * Function to display available metrics in the designer
 */
function displayMetrics(data) {
    console.log("Metrics: ");
    console.log(data);
    $('#analytics-metrics a.metric').remove();
    var i;
    for (i in data) {
        $("#analytics-metrics").append('<a type="checkbox" class="metric" href="#" id="' + data[i].id + '">' + data[i].name + '<select class="hidden"><option value="line">Line</option><option value="spline">Spline</option> <option value="area">Area</option><option value="column">Column</option><option value="bar">Bar</option><option value="pie">Pie</option><option value="x-axis">x-axis</option></select></a>');
        $("#" + data[i].id).data("metric", data[i]);
    }
}

/**
 * Load the Metrics and display
 */
function loadMetrics(doneFunction) {
    $.ajax({
        url: 'analytics/metrics'
    }).done(doneFunction).fail(function (jqXHR, textStatus, errorThrown) {
        showError("Failed to load the metrics. textStatus=" + textStatus + " errorThrown=" + errorThrown)
    });
}

/**
 * Avoid event bubble up from the metric select change
 */
$("body").on('click', '.sidebar a.metric select',function (event) {
    if (event.stopPropagation) {
        // standard
        event.stopPropagation();
    } else {
        // IE6-8
        event.cancelBubble = true;
    }
})

/**
 * On click of metric show chart types
 */
$("body").on('click', '#analytics-metrics .metric',function () {
    $(this).toggleClass("active");
    $(this).find("select").toggleClass("hidden");
})
