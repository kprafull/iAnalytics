package com.analytics.iAnalytics.dao.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(ReportRepository repository) {
        return args -> {
            //log.info("Preloading " + repository.save(new Report("test", "{}")));
        };
    }
}
