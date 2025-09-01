package com.cyboul.demo.web;

import com.cyboul.demo.data.PetRepository;
import com.cyboul.demo.model.pet.Animal;
import com.cyboul.demo.model.pet.Pet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PetController.class)
public class PetControllerTests {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;
    @MockitoBean PetRepository petRepository;

    private List<Pet> pets;

    @BeforeEach
    void setup(){
        pets.add(new Pet(101L, "Milou", "", Animal.DOG));
    }

    @Test
    void shouldFindAllPets() throws Exception {
        when(petRepository.findAll()).thenReturn(pets);
        mvc.perform(get("/api/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(pets.size()));
    }
}
