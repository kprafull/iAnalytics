package com.analytics.controller;

import com.analytics.iAnalytics.exception.ReportNotFoundException;
import com.analytics.iAnalytics.dao.model.Report;
import com.analytics.iAnalytics.dao.repository.ReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/analytics/report")
public class ReportController {
    @Autowired
    private ReportRepository reportRepository;

    @PostMapping(path="")
    public @ResponseBody
    ResponseEntity<Report> addNewReport (@RequestBody Report report) {
        log.info("Saving report {}", report);
        Report result = reportRepository.save(report);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping(path="")
    public @ResponseBody Iterable<Report> getAllReports() {
        // This returns a JSON with all the reports
        return reportRepository.findAll();
    }

    @GetMapping(path="/{id}")
    public @ResponseBody
    Report getReportById(@PathVariable Long id) {
        // This returns a JSON with the given id
        return reportRepository.findById(id).orElseThrow(() -> new ReportNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    void deleteReport(@PathVariable Long id) {
        reportRepository.deleteById(id);
    }
}
