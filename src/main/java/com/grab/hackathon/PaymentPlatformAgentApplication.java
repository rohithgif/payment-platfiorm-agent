package com.grab.hackathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = "com.grab.hackathon")
@EnableScheduling
public class PaymentPlatformAgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentPlatformAgentApplication.class, args);
		System.out.println("Application is running!!");
	}

}
