package com.example.personalizeddataservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PersonalizedDataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalizedDataServiceApplication.class, args);
	}

}
