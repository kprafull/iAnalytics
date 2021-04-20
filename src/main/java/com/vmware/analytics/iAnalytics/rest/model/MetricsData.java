package com.vmware.analytics.iAnalytics.rest.model;

import com.vmware.analytics.iAnalytics.dao.model.Metric;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricsData {
    //private String[] names;
    private Metric[] metrics;
    private List<Object[]> values;
}
