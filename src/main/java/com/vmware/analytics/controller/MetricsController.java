package com.vmware.analytics.controller;

import com.vmware.analytics.iAnalytics.dao.model.Metric;
import com.vmware.analytics.iAnalytics.dao.repository.MetricRepository;
import com.vmware.analytics.iAnalytics.exception.MetricNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/analytics/metrics")
public class MetricsController {
    @Autowired
    private MetricRepository metricRepository;

    @PostMapping(path="")
    public @ResponseBody
    ResponseEntity<Metric> addNewMetric(@RequestBody Metric metric) {
        log.info("Saving metric {}", metric);
        Metric resp = metricRepository.save(metric);
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @GetMapping(path="")
    public @ResponseBody
    ResponseEntity<List<Metric>> getAllMetrics() {
        // This returns a JSON with all the metrics
        return new ResponseEntity<>(metricRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public @ResponseBody
    Metric getMetricById(@PathVariable Long id) {
        // This returns a JSON with the given id
        return metricRepository.findById(id).orElseThrow(() -> new MetricNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    void deleteMetric(@PathVariable Long id) {
        metricRepository.deleteById(id);
    }
}
