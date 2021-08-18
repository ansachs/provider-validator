package com.sachs.jpmc.providervalidator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class AccountValidationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountValidationApplication.class, args);
    }
}
