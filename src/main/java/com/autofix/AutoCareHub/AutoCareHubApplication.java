package com.autofix.AutoCareHub;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AutoCareHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoCareHubApplication.class, args);
	}
/*
	@Bean
	CommandLineRunner init(){
		return args -> {
			c
		};
	}
*/
}
