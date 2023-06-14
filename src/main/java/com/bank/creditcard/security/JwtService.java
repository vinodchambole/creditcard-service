package com.bank.creditcard.security;

import com.bank.creditcard.security.user.User;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class JwtService {

    public UserDetails isTokenValid(String token) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        HttpEntity<String> requestEntity = new HttpEntity<>("{}", headers);
        URI accessTokenUri = URI.create("http://localhost:8080/validate-token");
        ResponseEntity<User> responseEntity = restTemplate.exchange(accessTokenUri, HttpMethod.POST, requestEntity, User.class);
        return responseEntity.getBody();
    }
}
