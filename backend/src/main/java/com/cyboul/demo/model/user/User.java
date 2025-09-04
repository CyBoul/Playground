package com.cyboul.demo.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public record User (
        @NotNull @Id Long id,
        @NotEmpty String username,
        @NotEmpty String password,
        @NotEmpty String email
){}
