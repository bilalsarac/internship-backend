package com.mobile;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MobileApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobileApplication.class, args);
	}

	@Bean
	public CommandLineRunner databaseInitializer(JdbcTemplate jdbcTemplate) {
		return args -> {
			String sql = "ALTER TABLE tax MODIFY image LONGBLOB";
			jdbcTemplate.execute(sql);
			System.out.println("Database column modified successfully.");
		};
	}

}