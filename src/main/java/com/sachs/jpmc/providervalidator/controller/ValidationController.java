package com.sachs.jpmc.providervalidator.controller;

import com.sachs.jpmc.providervalidator.model.AccountValidationRequest;
import com.sachs.jpmc.providervalidator.model.ValidationResponse;
import com.sachs.jpmc.providervalidator.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/account/validate")
@RequiredArgsConstructor
public class ValidationController {

    private final ValidationService validationService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ValidationResponse.ProviderResponse>> validateAccount(@RequestBody AccountValidationRequest accountValidationRequest) {
        List<ValidationResponse.ProviderResponse> validationResponse = validationService.validateAccount(accountValidationRequest);
        return ResponseEntity.ok(validationResponse);
    }
}
