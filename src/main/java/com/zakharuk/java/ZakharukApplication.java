package com.zakharuk.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ZakharukApplication {
	public static void main(String[] args) {
		SpringApplication.run(ZakharukApplication.class, args);
	}
}