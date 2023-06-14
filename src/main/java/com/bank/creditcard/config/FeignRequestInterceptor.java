package com.bank.creditcard.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class FeignRequestInterceptor implements RequestInterceptor {

    @Value("${oauth2.client.id}")
    private String clientId;

    @Value("${oauth2.client.password}")
    private String clientPassword;

    @Value("${oauth2.access-token-uri}")
    private String accessTokenUri;

    @Override
    public void apply(RequestTemplate requestTemplate) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(clientId, clientPassword);
        HttpEntity<AuthenticationRequest> requestEntity = new HttpEntity<>(authenticationRequest, headers);

        ResponseEntity<AuthenticationResponse> responseEntity = restTemplate.exchange(accessTokenUri, HttpMethod.POST, requestEntity, AuthenticationResponse.class);

        requestTemplate.header("Authorization", "Bearer " + responseEntity.getBody().getAccessToken());
    }
}
