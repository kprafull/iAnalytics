package com.analytics.iAnalytics.exception;

public class MetricNotFoundException extends RuntimeException{
    public MetricNotFoundException(Long id) {
        super("Could not find Metric " + id);
    }
}
