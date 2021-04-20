package com.analytics.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.analytics.iAnalytics.dao.model.Metric;
import com.analytics.iAnalytics.dao.service.FetchData;
import com.analytics.iAnalytics.rest.model.Attributes;
import com.analytics.iAnalytics.rest.model.MetricsData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Slf4j
@RestController
@RequestMapping(path = "/analytics")
public class AnalyticsController {
    @Autowired
    private MetricsController metricsController;

    @GetMapping("/query")
    public @ResponseBody
    MetricsData queryData(@RequestParam(value="attributes", required=true) String attrs) {
        long start = System.currentTimeMillis();
        log.info("Measure requested... {} with attributes {}", new Date(), attrs);


        Attributes attributes = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            attributes = mapper.readValue(attrs, Attributes.class);
            log.info("parsed - {}", attributes);

            Metric[] metrics = new Metric[attributes.getMetrics().length];
            int i = 0;
            for(String metricId : attributes.getMetrics()) {
                Metric metric = metricsController.getMetricById(Long.valueOf(metricId));
                metrics[i] = metric;
                i++;
            }
            attributes.setMetricObjects(metrics);
            log.info("filled - {}", attributes);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }




        MetricsData metricsData = new MetricsData();
        //metricsData.setNames(new String[] {"Date", "Temp", "Humidity"});
        metricsData.setMetrics(attributes.getMetricObjects());

        //define query
        String query = "select ";
        for(int i=0; i<metricsData.getMetrics().length; i++) {
            Metric metric = metricsData.getMetrics()[i];
            if(i==0) {
                query += metric.getColumnName() + " _col0 ";
            } else {
                query += "avg("+metric.getColumnName()+")";
            }
            if(i<metricsData.getMetrics().length-1) {
                query += ",";
            }
        }
        query += " from WEATHER group by _col0 order by _col0";
        //query += metricsData.getMetrics()[0].getColumnName();

        log.info("qeury {}", query);

        List<Object[]> result = FetchData.fetch(query);
        //List<Object[]> result = FetchData.fetch("select city,avg(avg_temp),avg(avg_humidity) from WEATHER group by city");
        //List<Object[]> result = FetchData.fetch("select city,avg_temp,avg_humidity from WEATHER");
        //List<Object[]> result = FetchData.fetch("select extract(epoch from date) * 1000,avg_temp,avg_humidity from WEATHER order by date");
        metricsData.setValues(result);
        log.info("Measure completed {}", (System.currentTimeMillis() - start));
        return metricsData;
    }

    private MetricsData fakeMeasure() {
        MetricsData measure = new MetricsData();
        //measure.setNames(new String[] {"City", "Temp", "Humidity", "Wind"});
        measure.setValues(fakeData());
        return measure;
    }

    private List fakeData() {
        Random rand = new Random();
        List<Object[]> l = new LinkedList<>();
        for(int i=0; i<10; i++) {
            l.add(new Object[]{i, rand.nextInt(1000), rand.nextInt(500), rand.nextInt(100)});
        }
        return l;
    }
}
