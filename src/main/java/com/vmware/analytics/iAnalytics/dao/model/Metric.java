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
@Table(name = "analytics_metrics")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Metric {
    public enum ColumnType {
        STRING,
        INT,
        LONG,
        NUMERIC,
        DATE,
        DATETIME;
    }

    public enum Database {
        POSTGRES,
        MYSQL,
        HADOOP;
    }

    public enum DatabaseType {
        SQL,
        NOSQL;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String columnName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ColumnType columnType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Database database;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DatabaseType databaseType;
}
