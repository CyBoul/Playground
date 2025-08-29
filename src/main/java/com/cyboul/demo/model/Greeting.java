package com.cyboul.demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class Greeting extends RepresentationModel<Greeting> {

    private final long id;
    private final String content;

    @JsonCreator
    public Greeting(@JsonProperty("id") long id,
                    @JsonProperty("content") String content)
    {
        this.id = id;
        this.content = content;
    }

}