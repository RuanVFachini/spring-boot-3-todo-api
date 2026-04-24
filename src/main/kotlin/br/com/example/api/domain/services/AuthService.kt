package br.com.example.api.domain.services

import br.com.example.api.domain.entities.User
import org.springframework.security.core.userdetails.UserDetails

interface AuthService {

    fun login(userName: String, password: String): String

    fun register(userName: String, password: String): User
}