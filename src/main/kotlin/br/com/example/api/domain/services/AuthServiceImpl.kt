package br.com.example.api.domain.services

import br.com.example.api.domain.entities.User
import br.com.example.api.domain.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class AuthServiceImpl(
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
    val tokenService: TokenService
) : AuthService {
    override fun login(
        userName: String,
        password: String
    ): String {
        val user = userRepository.findByUsername(userName).getOrElse {
            throw UsernameNotFoundException(userName)
        }
        return tokenService.generateToken(user)
    }

    override fun validateToken(token: String): UserDetails {
        val decoded = tokenService.validateToken(token.substringAfter("Bearer "))
        val userDetails = userRepository.findByUsername(decoded.subject).getOrElse {
            throw UsernameNotFoundException(decoded.subject)
        }
        return userDetails
    }

    override fun register(userName: String, password: String): User {
        return userRepository.save(User(userName, passwordEncoder.encode(password)))
    }
}