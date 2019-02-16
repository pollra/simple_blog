package com.pollra.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("application.yaml")
public class MyProperties {
    @Value("${spring.datasource.jdbcUrl}")
    private String jdbcUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.maximumPoolSize}")
    private int maximumPoolSize;
    @Value("${spring.datasource.auto-commit}")
    private boolean autoCommit;

    @Lazy
    @Bean(destroyMethod = "")
    public DataSource dataSource(){
        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setMaximumPoolSize(maximumPoolSize);
        hikariConfig.setAutoCommit(autoCommit);

        hikariConfig.setLeakDetectionThreshold(2000);
        hikariConfig.setPoolName("pollra_portfolio_blog");

        final HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        return dataSource;
    }
}
