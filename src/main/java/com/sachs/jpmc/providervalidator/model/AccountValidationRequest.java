package com.sachs.jpmc.providervalidator.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AccountValidationRequest {
    private String accountNumber;
    private List<String> providers;
}
