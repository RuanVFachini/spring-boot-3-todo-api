package br.com.example.api.domain.services

import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.security.core.userdetails.UserDetails

interface TokenService {
    fun generateToken(userDetails: UserDetails): String
    fun validateToken(token: String?): DecodedJWT
}