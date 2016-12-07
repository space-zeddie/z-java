package com.zakharuk.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ZakharukApplication {
	private static int PRODUCTION_STAGE = 1;

	public static void main(String[] args) {
		SpringApplication.run(ZakharukApplication.class, args);
	}

	public static void moveProductionStage(int s) {
		if (s == 1 || s == 2 || s == 3)
			PRODUCTION_STAGE = s;
		else return;
	}

	public static int getProductionStage() {
		return PRODUCTION_STAGE;
	}
}