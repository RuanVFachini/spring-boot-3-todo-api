package br.com.example.api.domain.repositories

import br.com.example.api.domain.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun findByUsername(email: String): Optional<UserDetails>
}