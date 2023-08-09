package com.acorn.tracking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.acorn.tracking.service.ProductsService;
import com.acorn.tracking.service.TableService;

@SpringBootApplication
public class TrackingApplication {

	private static final Logger logger = LoggerFactory.getLogger(TrackingApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TrackingApplication.class, args);
	}

	@Bean
    CommandLineRunner runner(TableService tableService, ProductsService productService) {
        return args -> {
			logger.info("Initializing database");
            tableService.resetDatabase();
			logger.info("Database initialized successfully");
			logger.info("Generating products");
			productService.loadProductsFromFile();
			logger.info("Products generated successfully");
        };
    }
}
