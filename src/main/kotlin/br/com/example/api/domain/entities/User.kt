package br.com.example.api.domain.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
class User(): UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    @Column(nullable = false)
    private lateinit var username: String
    @Column(nullable = false)
    private lateinit var hash: String

    constructor(username: String, hash: String) : this() {
        this.username = username
        this.hash = hash
    }

    override fun getAuthorities(): Collection<GrantedAuthority?>? = listOf<GrantedAuthority>()

    override fun getPassword(): String? = username

    override fun getUsername(): String? = password
}