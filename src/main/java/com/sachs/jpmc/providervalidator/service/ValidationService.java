package com.sachs.jpmc.providervalidator.service;

import com.sachs.jpmc.providervalidator.model.AccountValidationRequest;
import com.sachs.jpmc.providervalidator.model.ProviderList;
import com.sachs.jpmc.providervalidator.model.ValidationResponse;
import com.sachs.jpmc.providervalidator.web.ValidationClientRequests;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ValidationService {

    private final ProviderList providerList;
    private final ValidationClientRequests validationClient;

    public List<ValidationResponse.ProviderResponse> validateAccount(AccountValidationRequest accountValidationRequest) {
        Map<String, ProviderList.Provider> providerNameUrlMap = providerList.toMap();
        List<ProviderList.Provider> providers = accountValidationRequest.getProviders().isEmpty()
                ? providerList.getProviders()
                : accountValidationRequest.getProviders().stream()
                .map(String::toLowerCase)
                .map(providerNameUrlMap::get)
                .collect(Collectors.toList());

        return providers
                .parallelStream()
                .map(provider -> validationClient.validateAccount(provider, accountValidationRequest.getAccountNumber()))
                .collect(Collectors.toList());
    }
}
