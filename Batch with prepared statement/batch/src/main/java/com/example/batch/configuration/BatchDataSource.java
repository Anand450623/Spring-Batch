package com.example.batch.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class BatchDataSource 
{
	@Bean
	public DataSource mysqlDataSource() 
	{
		HikariConfig config= new HikariConfig();
        config.setDriverClassName("org.hsqldb.jdbcDriver");
        config.setJdbcUrl("jdbc:hsqldb:hsql://localhost/");
        config.setUsername("SA");
        config.setPassword("");
        config.setMinimumIdle(600);
        config.setMaximumPoolSize(30);
        config.setConnectionTimeout(251);
        config.setMaxLifetime(250);
        config.setAutoCommit(false);
        return new HikariDataSource(config);
    }
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource)
	{
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
		return jdbcTemplate;
	}
}
