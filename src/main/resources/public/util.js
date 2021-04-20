//unique id for chart container
var g_container_id = 1;

/**
 * Check if the string is empty
 * @param str
 * @returns {boolean}
 */
function isEmpty(str) {
    return (!str || 0 === str.length);
}

/**
 * Utility method to generate a unique id for containers
 */
function generateContainerId() {
    g_container_id++;
    return "chart_"+g_container_id;
}

/**
 * Assign an id to the containder if does not exist
 * @param container
 */
function assignContainerId(container) {
    var id = container.attr('id');
    if(id === undefined) {
        id = generateContainerId();
        container.attr('id', id);
    }
    return id;
}

/**
 * Change the container border
 */
function changeContainerBorder(container, color) {
    container.css("border", "2px solid "+color);
}

/**
 * Convert the data from the backend format to series and categories data
 *
 * Pie needs first point as string e.g. data: [["city-1", 1], ["city-2", 2], ["city-3",3]]
 * others if xAxis type is string use categories: [20, 100, 150]
 * else if xAsis is numerical use data: [[100, 1], [200, 2], [300,3]]
 *
 * @param result
 * @param chart_attributes
 * @returns {{categories: Array, series: Array}}
 */
function toSeriesData(result, chart_attributes) {
    var series = [];
    var categories = [];

    for(var i=0; i<result.metrics.length-1; i++) {
        series[i] = {};
        series[i].type = chart_attributes.type[i+1];
        series[i].name = result.metrics[i+1].name;
        series[i].data = [];

        var isPie = false;
        if(series[i].type === 'pie') {
            isPie = true;
        }

        var isCategoryRequired;
        if(!(typeof result.values[0][0] === 'string' || result.values[0][0] instanceof String) || series[i].type === 'pie') {
            isCategoryRequired = false;
        } else {
            isCategoryRequired = true;
        }

        $.each(result.values, function() {
            var row = this;
            if(isCategoryRequired) {
                //row[0] is X axis
                categories.push(row[0]);
                //next columns are Y axis
                series[i].data.push(row[i+1]);
            } else {
                if(isPie) {
                    series[i].data.push([row[0].toString(), row[i + 1]]);
                } else {
                    series[i].data.push([row[0], row[i + 1]]);
                }
            }
        });
    }

    var type = result.metrics[0].columnType.toLowerCase();
    return {categories: categories, series: series, xAxisType: type};
}

/**
 * Draw the highchart on a given container
 *
 * @param id: id of chart container
 * @param data: The metrics data to draw
 * @param chart_attributes: Attributes of chart e.g. chart type
 */
function chart(id, data, chart_attributes) {
    var categories;
    if(data.categories == undefined || data.categories.length == 0) {
        categories = undefined;
    } else {
        categories = data.categories;
    }

    Highcharts.chart(id, {
        chart: {
            zoomType: 'x'
        },
        colors: ['#2f7ed8', '#8bbc21', '#910000', '#1aadce',
            '#492970', '#f28f43', '#77a1e5', '#c42525', '#a6c96a', '#0d233a'],
        title: {
            text: chart_attributes.title
        },
        xAxis: {
            categories: categories,
            //Needed this for date type
            //type: 'datetime',
            type: data.xAxisType,
            title: {
                text: chart_attributes.xAxis
            }
        },
        yAxis: {
            title: {
                text: chart_attributes.yAxis
            }
        },

        series: data.series,

        credits: {
            enabled:false
        }

    });
}

/**
 * Initiate the draw in the target container
 * - validate container
 * - Assign container id if required
 * - Assocate the chart_attributes and data with the container
 * - Load the measures data
 * @param container
 * @param chart_attributes
 */
function initiateDraw(container, chart_attributes) {
    if(container === undefined) {
        showError("Please select the container to draw");
        return;
    }
    var id = assignContainerId(container);
    showMessage(container, "Waiting...");

    console.log(chart_attributes);
    container.data('chart_attributes', chart_attributes);

    $.ajax({
        url: 'analytics/query',
        data: {
            attributes: JSON.stringify(chart_attributes)
        }
    }).done(function (result) {
        console.log(result);
        chart_attributes.xAxis = result.metrics[0].name;
        var data = toSeriesData(result, chart_attributes);
        console.log(data);
        container.data('data', data);
        chart(id, data, chart_attributes);
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.log("failed textStatus="+textStatus+" errorThrown="+errorThrown);
        console.log(jqXHR);
        showMessage(container, "Failed with error - "+jqXHR.status);
    });
}
