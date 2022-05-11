package com.github.chMatvey.chaosTool.chaosClientsStarter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.chMatvey.chaosTool.chaosClientsStarter.ChaosClientException;
import com.github.chMatvey.chaosTool.chaosModels.ChaosCreateRequest;
import com.github.chMatvey.chaosTool.chaosModels.ChaosResponse;
import com.github.chMatvey.chaosTool.chaosModels.ChaosUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class ChaosClientImpl implements ChaosClient {
    private final RestTemplate restTemplate;

    public static final String CHAOS_SERVER_API_URL = "http://localhost:10501/api/v1/chaos-server";

    @Override
    public ChaosResponse create(ChaosCreateRequest createRequest) {
        return restTemplate.postForObject(
                CHAOS_SERVER_API_URL,
                createHttpEntity(createRequest),
                ChaosResponse.class
        );
    }

    @Override
    public ChaosResponse update(ChaosUpdateRequest updateRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        HttpEntity<ChaosUpdateRequest> requestEntity = new HttpEntity<>(updateRequest, headers);
        ResponseEntity<ChaosResponse> responseEntity =
                restTemplate.exchange(CHAOS_SERVER_API_URL, PUT, requestEntity, ChaosResponse.class);
        if (responseEntity.getStatusCode() == OK && requestEntity.hasBody()) {
            return responseEntity.getBody();
        } else {
            throw new ChaosClientException("Cannot execute chaos update request");
        }
    }

    private HttpEntity<String> createHttpEntity(Object object) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        String jsonBodyAsString = jsonBodyAsString(object);
        return new HttpEntity<>(jsonBodyAsString, headers);
    }

    @SneakyThrows
    private String jsonBodyAsString(Object object) {
        return new ObjectMapper()
                .writer()
                .withDefaultPrettyPrinter()
                .writeValueAsString(object);
    }
}
