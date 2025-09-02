package com.cyboul.demo.web;

import com.cyboul.demo.data.PetRepository;
import com.cyboul.demo.model.pet.Animal;
import com.cyboul.demo.model.pet.Pet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * KO -- 403 instead -- spring-security?
 */
@WebMvcTest(PetController.class)
public class PetControllerTests {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;
    @MockitoBean PetRepository petRepository;

    private List<Pet> pets;

    @BeforeEach
    void setup(){
        pets = new ArrayList<>();
        pets.add(new Pet(101L, "Milou", "", Animal.DOG));
        pets.add(new Pet(102L, "Fang", "", Animal.DOG));
    }

    @Test
    void shouldFindAllPets() throws Exception {
        when(petRepository.findAll()).thenReturn(pets);
        mvc.perform(get("/api/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(pets.size()));
    }

    @Test
    void shouldFindOnePet() throws Exception {
        Pet pet = pets.getFirst();
        when(petRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(pet));
        mvc.perform(get("/api/pets/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(pet.getId()))
                .andExpect(jsonPath("$.name").value(pet.getName()))
                .andExpect(jsonPath("$.type").value(pet.getType().toString()))
                .andExpect(jsonPath("$.description").value(pet.getDescription()));
    }

    @Test
    void shouldReturnNotFound() throws Exception {
        when(petRepository.findById(90101L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mvc.perform(get("/api/pets/90101")).andExpect(status().isNotFound());
    }

    @Test
    void shouldCreate() throws Exception {
        Pet pet = new Pet(null, "Kuma", "Likes Bamboo", Animal.PANDA);
        mvc.perform(post("/api/pets")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(pet)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldEdit() throws Exception {
        Pet pet = pets.get(1);
        pet.setName("Renamed");
        when(petRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(pet));

        mvc.perform(put("/api/pets/" + pet.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(pet)))
                .andExpect(status().isNoContent());

        mvc.perform(get("/api/pets/" + pet.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(pet.getId()))
                .andExpect(jsonPath("$.name").value(pet.getName()))
                .andExpect(jsonPath("$.type").value(pet.getType().toString()))
                .andExpect(jsonPath("$.description").value(pet.getDescription()));
    }

    @Test
    void shouldDelete() throws Exception {
        when(petRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(pets.getFirst()));
        mvc.perform(delete("/api/pets/1"))
                .andExpect(status().isNoContent());
    }
}
