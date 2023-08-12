package com.acorn.tracking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.acorn.tracking.service.AdminsService;
import com.acorn.tracking.service.ProductsService;
import com.acorn.tracking.service.TableService;

@SpringBootApplication
@EnableScheduling
public class TrackingApplication {

	private static final Logger logger = LoggerFactory.getLogger(TrackingApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TrackingApplication.class, args);
	}

	@Bean
	// 이 메서드는 애플리케이션을 실행할 때 자동으로 실행되는 초기화 러너입니다.
	CommandLineRunner runner(TableService tableService, ProductsService productService, AdminsService adminsService) {
		return args -> {
			// 데이터베이스 초기화 작업 수행
			logger.info("Initializing database");
			tableService.resetDatabase();
			logger.info("Database initialized successfully");
			// 제품 생성 작업 수행
			logger.info("Generating products");
			productService.loadProductsFromFile();
			logger.info("Products generated successfully");
			// 관리자 생성 작업 수행
			logger.info("Generating Admins");
			adminsService.insertAdmins();
			logger.info("Admins generated successfully");
		};
	}
}