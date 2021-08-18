package com.sachs.jpmc.providervalidator.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfiguration {

    @Bean
    public WebClient.Builder baseClient() {
        return WebClient.builder();
    }
}
