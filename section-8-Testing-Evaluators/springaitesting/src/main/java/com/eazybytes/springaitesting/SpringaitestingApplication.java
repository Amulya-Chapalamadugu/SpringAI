package com.eazybytes.springaitesting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class SpringaitestingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringaitestingApplication.class, args);
	}

}
