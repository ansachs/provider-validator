package com.sachs.jpmc.providervalidator.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Configuration
@Accessors(chain = true)
@ConfigurationProperties("jpmc")
public class ProviderList {

    private List<Provider> providers;

    @Data
    public static class Provider {
        private String name;
        private String url;
    }

    public Map<String, Provider> toMap() {
        return providers.stream()
                .collect(Collectors.toMap(p -> p.getName().toLowerCase(), provider -> provider));
    }

}
