package com.eazybytes.support_agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling//to scan the mail inbox for every 10 secs
@ConfigurationPropertiesScan
public class SupportAgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupportAgentApplication.class, args);
	}

}
