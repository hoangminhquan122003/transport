package com.transporthc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class TransporthcApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransporthcApplication.class, args);
	}

}
