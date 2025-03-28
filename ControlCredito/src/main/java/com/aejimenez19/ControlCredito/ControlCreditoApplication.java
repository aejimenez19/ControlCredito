package com.aejimenez19.ControlCredito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ControlCreditoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControlCreditoApplication.class, args);
	}

}
