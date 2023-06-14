package com.bank.creditcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.bank.creditcard"})
public class CreditCardApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditCardApplication.class, args);
	}
}
