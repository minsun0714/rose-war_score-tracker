package com.rosewar.scoretracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ScoretrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScoretrackerApplication.class, args);
	}

}
