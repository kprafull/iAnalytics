package com.analytics.iAnalytics.exception;

public class ReportNotFoundException extends RuntimeException{
    public ReportNotFoundException(Long id) {
        super("Could not find Report " + id);
    }
}
