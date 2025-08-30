package com.cyboul.demo.web;

import com.cyboul.demo.data.PetRepository;
import com.cyboul.demo.model.pet.Pet;
import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetRepository repository;

    public PetController(PetRepository repo){
        this.repository = repo;
    }

    @GetMapping("")
    public HttpEntity<List<Pet>> viewAll(){

        List<Pet> pets = repository.findAll();

        pets.forEach(p -> {
            p.add(linkTo(methodOn(PetController.class)
                    .viewOne(p.getId()))
                    .withSelfRel());
            p.add(linkTo(methodOn(PetController.class)
                    .viewAll())
                    .withRel("all"));
        });

        return new ResponseEntity<>(pets, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public HttpEntity<Pet> viewOne(@PathVariable Long id){

        Optional<Pet> optPet = repository.findById(id);
        if (optPet.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Pet pet = optPet.get();
        pet.add(linkTo(methodOn(PetController.class)
                    .viewOne(pet.getId()))
                    .withSelfRel());
        pet.add(linkTo(methodOn(PetController.class)
                    .viewAll())
                    .withRel("all"));

        return new ResponseEntity<>(pet, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @PostMapping("")
    public void create(@Valid @RequestBody Pet pet){
        //
    }

    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @PutMapping("/{id}")
    public void update(@PathVariable Long id){
        //
    }

    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        //
    }


}
