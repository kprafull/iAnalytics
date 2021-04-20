package com.analytics.iAnalytics.dao.repository;

import com.analytics.iAnalytics.dao.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByName(String name);
}
