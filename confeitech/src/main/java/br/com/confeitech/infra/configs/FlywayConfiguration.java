package br.com.confeitech.infra.configs;

import jakarta.annotation.PostConstruct;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfiguration {

    private final DataSource dataSource;

    @Autowired
    public FlywayConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void migrate() {
        Flyway.configure().baselineOnMigrate(true).dataSource(dataSource).load().migrate();
    }
}