package com.cyboul.demo.web;

import com.cyboul.demo.logic.data.PetRepository;
import com.cyboul.demo.model.pet.Pet;
import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
        pets.forEach(PetController::addLinks);
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void create(@Valid @RequestBody Pet pet){
        repository.save(pet);
    }

    @GetMapping("/{id}")
    public HttpEntity<Pet> viewOne(@PathVariable Long id){
        return repository.findById(id)
                .map(pet -> new ResponseEntity<>(addLinks(pet), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @Valid @RequestBody Pet pet){
        repository.findById(id)
                .map(p -> repository.save(pet))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        repository.findById(id)
                .map(p -> { repository.deleteById(p.getId()); return -1; })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private static Pet addLinks(Pet pet){
        pet.add(linkTo(methodOn(PetController.class)
                .viewOne(pet.getId()))
                .withSelfRel());
        pet.add(linkTo(methodOn(PetController.class)
                .viewAll())
                .withRel("all"));
        return pet;
    }


}
