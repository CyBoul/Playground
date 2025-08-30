package com.cyboul.demo.model.pet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@Entity
public class Pet extends RepresentationModel<Pet> {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Animal type;

    public Pet (){}

    @JsonCreator
    public Pet (@JsonProperty Long id,
            @JsonProperty String name,
            @JsonProperty String description,
            @JsonProperty Animal type
    ){
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
    }
}
