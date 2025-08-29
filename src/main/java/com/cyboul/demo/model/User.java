package com.cyboul.demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class User extends RepresentationModel<User> {

    private final long id;
    private final String username;

    @JsonCreator
    public User(@JsonProperty("id") long id,
                    @JsonProperty("username") String username)
    {
        this.id = id;
        this.username = username;
    }

}
