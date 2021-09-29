package com.bytesfly.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.bytesfly.jwt", "com.bytesfly.validation.component"})
public class JwtSwaggerApp {

    public static void main(String[] args) {
        SpringApplication.run(JwtSwaggerApp.class);
    }
}
