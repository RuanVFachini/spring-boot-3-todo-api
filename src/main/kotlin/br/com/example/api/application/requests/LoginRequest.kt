package br.com.example.api.application.requests

import jakarta.validation.constraints.NotEmpty

data class LoginRequest(
    @param:NotEmpty val email: String,
    @param:NotEmpty val password: String
)
