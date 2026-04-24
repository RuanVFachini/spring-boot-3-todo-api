package br.com.example.api.application.filters

import br.com.example.api.application.configs.ApiKeyAuthenticationToken
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import java.io.IOException


class ApiKeyAuthFilter(requiresAuth: RequestMatcher) : AbstractAuthenticationProcessingFilter(requiresAuth) {
    @Throws(AuthenticationException::class, IOException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse?): Authentication? {
        val apiKey = request.getHeader(API_KEY_HEADER) ?: throw RuntimeException("Missing API Key or Secret")

        val auth: Authentication = ApiKeyAuthenticationToken(apiKey, apiKey, mutableListOf(SimpleGrantedAuthority("USER")))
        return authenticationManager.authenticate(auth)
    }

    @Throws(IOException::class, ServletException::class)
    protected override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain,
        authResult: Authentication?
    ) {
        SecurityContextHolder.getContext().authentication = authResult
        chain.doFilter(request, response)
    }

    companion object {
        private const val API_KEY_HEADER = "X-API-Key"
    }
}