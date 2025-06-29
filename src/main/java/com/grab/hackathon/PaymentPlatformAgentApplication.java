package com.grab.hackathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.grab.hackathon")
public class PaymentPlatformAgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentPlatformAgentApplication.class, args);
	}

}
