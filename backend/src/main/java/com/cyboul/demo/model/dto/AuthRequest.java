package com.cyboul.demo.model.dto;

import jakarta.validation.constraints.NotEmpty;

public record AuthRequest(
        @NotEmpty String email,
        @NotEmpty String password
){}