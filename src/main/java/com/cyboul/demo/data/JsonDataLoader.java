package com.cyboul.demo.data;

import com.cyboul.demo.model.pet.Pets;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * Load JSON data after application has booted
 * /resources/data/Pets.json
 *
 */
@Component
public class JsonDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(JsonDataLoader.class);

    private final PetRepository petRepository;
    private final ObjectMapper objMapper;

    public JsonDataLoader(PetRepository petRepository, ObjectMapper objMapper) {
        this.petRepository = petRepository;
        this.objMapper = objMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if( petRepository.count() == 0 ){
            try (InputStream is = getClass().getResourceAsStream("/data/pets.json")){
                Pets pets = objMapper.readValue(is, Pets.class);
                log.info("Reading {} pets from JSON data and create them in the database", pets.pets().size());
                petRepository.saveAll(pets.pets());

            } catch (IOException e) {
               throw new RuntimeException("Failed to load JSON data", e);
            }
        } else {
            log.info("Data already stored !");
        }
    }
}
