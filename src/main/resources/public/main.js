(function () {
    console.log("js loaded")
    //current container for the chart to draw
    var g_container;

    /**
     * On double click on chart container, toggle the size of it
     */
    $("body").on('dblclick', 'div.chart',function () {
        $(this).toggleClass("normal-width-chart full-width-chart");
        //refresh chart
        refreshChart($(this));
    })

    /**
     * Handle the event to add a new container
     */
    $("#container-add").click(function () {
        $(".chart-row").append('<div class="chart"></div>');
    })

    /**
     * Handle the event to delete the selected container
     */
    $("#container-delete").click(function () {
        if(g_container != undefined) {
            g_container.remove();
            $("#id-next-chart-button").click();
        }
    })

    /**
     * Handle the event to clear the selected container
     */
    $("#container-clear").click(function () {
        if(g_container != undefined) {
            g_container.empty();
            g_container.removeData(["data", "chart_attributes"]);
        }
    })

    /**
     * For the top menu click event
     */
    $("#main-menubar a").click(function (e) {
        e.preventDefault();
        $(this).parent().find('a').removeClass("active");
        $(this).addClass("active");
        switch ($(this).attr('href')) {
            case '#':
                $('#main-designer').css({"display": "none"});
                $('#main-dashboard').css({"display": "block"});
                break;
            case '#designer':
                $('#main-dashboard').css({"display": "none"});
                $('#main-designer').css({"display": "block"});
                loadReports(displayReports);
                loadMetrics(displayMetrics);
                break;
        }
    });

    /**
     * On click of the prev container selection
     */
    $("#id-prev-chart-button").click(function() {
        if(g_container === undefined) {
            g_container = $("#chart-row-designer div.chart:first-child");
        } else {
            if(g_container.prev().length == 0) {
                return;
            }
            changeContainerBorder(g_container, "white");
            g_container = g_container.prev();
        }
        changeContainerBorder(g_container, "gray");
    });

    /**
     * On click of the next chart selection
     */
    $("#id-next-chart-button").click(function() {
        if(g_container === undefined) {
            g_container = $("#chart-row-designer div.chart:first-child");
        } else {
            if(g_container.next().length == 0) {
                return;
            }
            changeContainerBorder(g_container, "white");
            g_container = g_container.next();
        }
        changeContainerBorder(g_container, "gray");
    });

    /**
     * On click on the chart container
     */
    $("body").on('click', '#main-designer div.chart',function () {
        if(g_container != undefined) {
            changeContainerBorder(g_container, "white");
        }
        g_container = $(this);
        changeContainerBorder(g_container, "gray");
    })

    /**
     * Refresh chart in the container, when the div size changes. Note
     * that it does not load the data again
     */
    function refreshChart(container) {
        if(container.data('data') != undefined)
            chart(container.attr('id'), container.data('data'), container.data('chart_attributes'));
    }

    /**
     * Refresh all the charts
     */
    $("#graph-refresh-all").click(function () {
        var container = $("#chart-row-designer div.chart:first-child");
        while(container.length == 1) {
            refreshChart(container);
            container = container.next();
        }
    });

    /**
     * Click on the Draw
     */
    $("#graph-save").click(function () {
        //Get chart_attributes from container
        var attributes = g_container.data("chart_attributes");
        if(attributes == undefined) {
            showError("Please Draw the chart first on the selected container...");
        }
        var data = {name: attributes.title, data: JSON.stringify(attributes)};
        data = JSON.stringify(data);
        console.log("Saving Chart - "+data);
        $.ajax({
            type: "POST",
            url: '/analytics/report',
            contentType:"application/json; charset=utf-8",
            data: data
        }).done(function (result) {
            showError("Successfully saved report - ", data);
            loadReports(displayReports);
        }).fail(function (jqXHR, textStatus, errorThrown) {
            showError("Failed to save report with error: textStatus="+textStatus+" errorThrown="+errorThrown);
        });
    });

    /**
     * Click on the Draw
     * - generate chart attributes
     * - load the chart data
     * - draw chart
     */
    $("#graph-draw").click(function () {
        var chart_attributes = {
            type: [],
            title: $("#graph-title").val(),
            xAxis: undefined,
            yAxis: undefined,
            seriesColors: [],
            //metrics: [],
            metrics: [],
            groupBy: []
        };

        if($('#analytics-metrics a.metric.active').length == 0) {
            showError("Please select some metrics for visualization!");
            return;
        }

        //finding the selected metrics
        //first select the x-axis
        $('#analytics-metrics a.metric.active').each(function () {
            if($(this).find("select").val() == 'x-axis') {
                var metric = $(this).data("metric");
                chart_attributes.metrics.push(metric.id);
                chart_attributes.type.push($(this).find("select").val());
                console.log(metric);
            }
        });

        if(chart_attributes.metrics.length != 1) {
            showError("Please select one Metric of type x-axis");
            return;
        }

        //select all other selected metrics
        $('#analytics-metrics a.metric.active').each(function () {
            if($(this).find("select").val() != 'x-axis') {
                var metric = $(this).data("metric");
                chart_attributes.metrics.push(metric.id);
                chart_attributes.type.push($(this).find("select").val());
                console.log(metric);
            }
        });

        if(chart_attributes.metrics.length < 2) {
            showError("Please select minimum of two Metrics");
            return;
        }
        if(isEmpty(chart_attributes.title)) {
            showError("Please provide the Title of the graph");
            return;
        }
        initiateDraw(g_container, chart_attributes);
    });

    /**
     * On click of a report, its drawn in a container
     */
    $("body").on('click', '#analytics-reports a.report',function () {
        var chart_attributes = $(this).data("chart_attributes");
        initiateDraw(g_container, chart_attributes);
    })

    /**
     *
     * @param reports
     */
    function displayDashboard(reports) {
        var r;
        for (r in reports) {
            var target = $("#main-dashboard").append('<div class="chart normal-width-chart"></div>').children('div:last-child');
            initiateDraw(target, JSON.parse(reports[r].data));
            console.log("displayed report "+reports[r].id);
        }
    }

    loadReports(displayDashboard);
    $("#id-prev-chart-button").click();
})();
