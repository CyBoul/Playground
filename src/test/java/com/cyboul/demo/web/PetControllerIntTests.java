package com.cyboul.demo.web;

import com.cyboul.demo.model.pet.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@PropertySource("classpath:application-test.properties")
@Profile("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PetControllerIntTests {

    @LocalServerPort private int randomPort;
    private RestClient client;

    @BeforeEach
    void setup(){
        client = RestClient.create("http://localhost:" + randomPort);
    }

    @Test
    void shouldFindPets() throws Exception {
        List<Pet> pets = client.get()
                .uri("/api/pets")
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});

        assertNotNull(pets);
        assertFalse(pets.isEmpty());
    }
}
