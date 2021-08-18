package com.sachs.jpmc.providervalidator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ValidationResponse {
    private List<ProviderResponse> result;

    @Data
    @Builder
    public static class ProviderResponse {
        private String provider;
        @JsonProperty("isValid")
        private boolean isValid;
    }

}
