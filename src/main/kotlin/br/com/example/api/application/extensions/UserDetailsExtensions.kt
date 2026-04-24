package br.com.example.api.application.extensions

import br.com.example.api.application.responses.RegisterUserResponse
import br.com.example.api.domain.entities.User
import org.springframework.security.core.userdetails.UserDetails

fun UserDetails.toResponse(): RegisterUserResponse = RegisterUserResponse(username = username)