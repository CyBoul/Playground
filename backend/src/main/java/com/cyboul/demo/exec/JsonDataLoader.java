package com.cyboul.demo.exec;

import com.cyboul.demo.data.PetRepository;
import com.cyboul.demo.data.UserRepository;
import com.cyboul.demo.model.pet.Pets;
import com.cyboul.demo.model.user.Users;
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
 * /resources/data/Users.json
 *
 */
@Component
public class JsonDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(JsonDataLoader.class);

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final ObjectMapper objMapper;

    public JsonDataLoader(UserRepository userRepository, PetRepository petRepository, ObjectMapper objMapper) {
        this.userRepository = userRepository;
        this.petRepository = petRepository;
        this.objMapper = objMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        createPets();
        createUsers();
    }

    private void createUsers() {
        if( userRepository.findAll().isEmpty() ){
            try (InputStream is = getClass().getResourceAsStream("/data/users.json")){
                Users users = objMapper.readValue(is, Users.class);
                log.info("Reading and injecting {} users from JSON into the database", users.users().size());
                userRepository.saveAll(users.users());

            } catch (IOException e) {
                throw new RuntimeException("Failed to load JSON Users data", e);
            }
        } else {
            log.info("Users Data already stored !");
        }
    }

    private void createPets() {
        if( petRepository.findAll().isEmpty() ){
            try (InputStream is = getClass().getResourceAsStream("/data/pets.json")){
                Pets pets = objMapper.readValue(is, Pets.class);
                log.info("Reading and injecting {} pets from JSON into the database", pets.pets().size());
                petRepository.saveAll(pets.pets());

            } catch (IOException e) {
                throw new RuntimeException("Failed to load JSON Pets data", e);
            }
        } else {
            log.info("Pets Data already stored !");
        }
    }
}
