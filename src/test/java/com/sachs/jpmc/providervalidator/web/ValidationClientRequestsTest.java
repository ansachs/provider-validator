package com.sachs.jpmc.providervalidator.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sachs.jpmc.providervalidator.model.AccountValidationResponse;
import com.sachs.jpmc.providervalidator.model.ProviderList;
import com.sachs.jpmc.providervalidator.model.ValidationResponse;
import lombok.SneakyThrows;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class ValidationClientRequestsTest {

    private static MockWebServer mockWebServer;
    private static ObjectMapper mapper;
    private static ValidationClientRequests subject;

    @BeforeAll
    static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        mapper = new ObjectMapper();
        subject = new ValidationClientRequests(WebClient.builder());
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void validateAccount_onSuccess_returnProviderResponse() throws InterruptedException, JsonProcessingException {
        Dispatcher dispatcher = new Dispatcher() {

            @SneakyThrows
            @Override
            public MockResponse dispatch(RecordedRequest recordedRequest) {
                switch (recordedRequest.getPath()) {
                    case "/1/v1/api/account/validate":
                        return new MockResponse().setResponseCode(200)
                                .addHeader("Content-Type", "application/json")
                                .setBody(responseToJson(true));
                    case "/2/v1/api/account/validate":
                        return new MockResponse().setResponseCode(200)
                                .addHeader("Content-Type", "application/json")
                                .setBody(responseToJson(false));
                }
                return new MockResponse().setResponseCode(404);
            }
        };
        mockWebServer.setDispatcher(dispatcher);


        ProviderList.Provider provider1 = buildProvider("provider1", 1);
        ProviderList.Provider provider2 = buildProvider("provider2", 2);

        ValidationResponse.ProviderResponse response = subject.validateAccount(provider1, "some account");
        ValidationResponse.ProviderResponse responseTwo = subject.validateAccount(provider2, "some account");
        assertThat(response.isValid()).isTrue();
        assertThat(responseTwo.isValid()).isFalse();
    }

    private ProviderList.Provider buildProvider(String name, int serverId) {
        return new ProviderList.Provider()
                .setName(name)
                .setUrl(String.format("http://localhost:%s/%s/v1/api/account/validate", mockWebServer.getPort(), serverId));
    }

    private String responseToJson(boolean isValid) throws JsonProcessingException {
        return mapper.writeValueAsString(AccountValidationResponse.builder().isValid(isValid)
                .build());
    }
}