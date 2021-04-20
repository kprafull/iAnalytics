package com.vmware.analytics.iAnalytics.dao.repository;

import com.vmware.analytics.iAnalytics.dao.model.Metric;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricRepository extends JpaRepository<Metric, Long> {
}
