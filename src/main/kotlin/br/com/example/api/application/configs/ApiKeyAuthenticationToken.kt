package br.com.example.api.application.configs

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority


class ApiKeyAuthenticationToken : AbstractAuthenticationToken {
    private val apiKey: String?

    constructor(apiKey: String?, apiSecret: String?) : super(null) {
        this.apiKey = apiKey
        setAuthenticated(false)
    }

    constructor(apiKey: String?, apiSecret: String?, authorities: MutableCollection<out GrantedAuthority?>?) : super(
        authorities
    ) {
        this.apiKey = apiKey
        super.setAuthenticated(true)
    }

    override fun getCredentials(): Any? {
        return this.apiKey
    }

    override fun getPrincipal(): Any? {
        return this.apiKey
    }

    @Throws(IllegalArgumentException::class)
    override fun setAuthenticated(isAuthenticated: Boolean) {
        require(!isAuthenticated) { "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead" }
        super.setAuthenticated(false)
    }

    override fun eraseCredentials() {
        super.eraseCredentials()
    }
}