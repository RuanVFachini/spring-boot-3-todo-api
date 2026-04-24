package br.com.example.api.domain.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.Instant

@Entity
class Todo(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @Column(nullable = false)
    var description: String,
    @Column(nullable = false)
    var completed: Boolean,
    @Column(nullable = false)
    var createdAt: Instant,
    @Column(nullable = true)
    var completedAt: Instant? = null,
)