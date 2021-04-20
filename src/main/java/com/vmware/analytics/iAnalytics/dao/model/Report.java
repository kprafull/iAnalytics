package com.vmware.analytics.iAnalytics.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Use to store a single report/chart metadata in inventory/database
 */
@Entity
@Table(name = "analytics_report")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Column(name = "data", length = 9999)
    private String data;

    public Report(String name, String data) {
        this.name = name;
        this.data = data;
    }

}
