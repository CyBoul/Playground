package com.cyboul.demo.web;

import com.cyboul.demo.model.externals.UserAPI;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * Retrieving Users from https://jsonplaceholder.typicode.com/users
 */
@Component
public class UserRestClient {

    private final RestClient restClient;

    public UserRestClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build();
    }

    public List<UserAPI> findAll(){
        return restClient.get()
                .uri("/users")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public UserAPI findById(Long id){
        return restClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .body(UserAPI.class);
    }
}
