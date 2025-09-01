package com.cyboul.demo.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest(UserRestClient.class)
public class UserRestClientTests {

    @Autowired MockRestServiceServer server;
    @Autowired UserRestClient client;
    @Autowired ObjectMapper mapper;

    @Test
    void shouldFindAll() throws JsonProcessingException {

    }
}
