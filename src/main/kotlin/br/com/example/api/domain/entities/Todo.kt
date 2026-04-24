package br.com.example.api.domain.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.Instant

@Entity
class Todo() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    @Column(nullable = false)
    lateinit var description: String
    @Column(nullable = false)
    var completed: Boolean = false
    @Column(nullable = false)
    lateinit var createdAt: Instant
    @Column(nullable = true)
    var completedAt: Instant? = null

    constructor(id: Long, description: String, completed: Boolean, createdAt: Instant) : this() {
        this.id = id
        this.description = description
        this.completed = completed
        this.createdAt = createdAt
    }
}