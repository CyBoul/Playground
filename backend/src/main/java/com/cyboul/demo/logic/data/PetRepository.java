package com.cyboul.demo.logic.data;

import com.cyboul.demo.model.pet.Pet;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends ListCrudRepository<Pet, Long> {

}
