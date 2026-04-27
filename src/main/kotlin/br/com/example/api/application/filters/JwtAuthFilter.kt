package br.com.example.api.application.filters

import br.com.example.api.domain.services.AuthService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthFilter(
    val authService: AuthService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorization = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (!authorization.isNullOrBlank() && authorization.startsWith(TOKEN_SCHEMA)) {
            val user = authService.validateToken(authorization.substringAfter(TOKEN_SCHEMA))
            val auth = UsernamePasswordAuthenticationToken(
                user,
                null,
                user.authorities)
            SecurityContextHolder.getContext().authentication = auth
        }

        filterChain.doFilter(request, response)
    }

    companion object {
        const val TOKEN_SCHEMA = "Bearer "
    }
}