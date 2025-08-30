package com.cyboul.demo.model.user;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record User (
        @NotNull @Id long id,
        @NotEmpty String username,
        String email
){}
