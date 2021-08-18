package com.sachs.jpmc.providervalidator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountValidationResponse {
    @JsonProperty("isValid")
    private boolean isValid;
}
