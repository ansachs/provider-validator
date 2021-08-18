package com.sachs.jpmc.providervalidator.web;

import com.sachs.jpmc.providervalidator.model.AccountValidationResponse;
import com.sachs.jpmc.providervalidator.model.ProviderList;
import com.sachs.jpmc.providervalidator.model.ValidationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class ValidationClientRequests {

    private final WebClient.Builder baseClient;

    public ValidationResponse.ProviderResponse validateAccount(ProviderList.Provider provider, String accountNumber) {
        String requestJson = "{\"accountNumber\": \"" + accountNumber + "\"}";
        return baseClient.baseUrl(provider.getUrl())
                .build()
                .post()
                .bodyValue(requestJson)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(AccountValidationResponse.class)
                .map(r -> toProviderResponse(r, provider))
                .block();
    }

    private ValidationResponse.ProviderResponse toProviderResponse(AccountValidationResponse accountValidationResponse, ProviderList.Provider provider) {
        return ValidationResponse.ProviderResponse.builder()
                .provider(provider.getName())
                .isValid(accountValidationResponse.isValid())
                .build();
    }


}
