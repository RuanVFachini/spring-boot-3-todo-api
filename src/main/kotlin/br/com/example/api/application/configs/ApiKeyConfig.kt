package br.com.example.api.application.configs

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ApiKeyConfig(
    @Value("\${spring.security.api-key}")
    val apiKey: String
)