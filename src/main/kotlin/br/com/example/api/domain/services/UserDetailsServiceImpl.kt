package br.com.example.api.domain.services

import br.com.example.api.domain.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import kotlin.jvm.optionals.getOrElse

class UserDetailsServiceImpl(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByEmail(username).getOrElse { throw UsernameNotFoundException(username) }
    }
}