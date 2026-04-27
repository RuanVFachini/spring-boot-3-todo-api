package br.com.example.api.application.configs

import br.com.example.api.application.filters.JwtAuthFilter
import br.com.example.api.domain.services.AuthService
import jakarta.servlet.DispatcherType
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
    @param:Lazy private val authService: AuthService
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain? {

        val jwtAuthFilter = JwtAuthFilter(authService)

        http
            .csrf { it.disable() }
            .cors { corsConfigurer -> corsConfigurer.configure(http) }
            .sessionManagement { managementSession ->
                managementSession.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests {
                it.dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                it.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                it.requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                it.requestMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
                it.requestMatchers(HttpMethod.GET, "/swagger-ui/*").permitAll()
                it.requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
                it.anyRequest().authenticated()
            }

            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling {
                it.authenticationEntryPoint { _, response, _ ->
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
                }
                it.accessDeniedHandler { _, response, _ ->
                    response.sendError(HttpServletResponse.SC_FORBIDDEN)
                }
            }
        return http.build()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(
        authConfig: AuthenticationConfiguration
    ): AuthenticationManager? {
        return authConfig.authenticationManager
    }
}