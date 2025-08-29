package com.cyboul.demo.model;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;

public record AdoptionDemand(
        @NotNull @Positive @Id Long id,
        @NotNull @Positive Long petId,
        @NotNull @Positive Long userId,
        @NotNull LocalDateTime creationTime,
        @Version Integer version
){}
