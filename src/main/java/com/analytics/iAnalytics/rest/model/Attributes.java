package com.analytics.iAnalytics.rest.model;

import com.analytics.iAnalytics.dao.model.Metric;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attributes {
    String[] type;
    String title;
    String xAxis;
    String yAxis;
    String[] seriesColors;
    //metrics: [],
    String[] metrics;
    Metric[] metricObjects;
    String[] groupBy;
}
