package br.com.example.api.domain.services

import br.com.example.api.domain.entities.User
import br.com.example.api.domain.repositories.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
    val authenticationManager: AuthenticationManager
) : AuthService {
    override fun login(
        userName: String,
        password: String
    ): UserDetails {
        var userAndPass = UsernamePasswordAuthenticationToken(userName, password)
        val authentication = authenticationManager.authenticate(userAndPass)
        SecurityContextHolder.getContext().authentication = authentication
        return
    }

    override fun register(userName: String, password: String): User {
        return userRepository.save(User(userName, passwordEncoder.encode(password)))
    }
}