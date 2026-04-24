package br.com.example.api.application.configs

import br.com.example.api.application.filters.JwtAuthFilter
import jakarta.servlet.DispatcherType
import org.springframework.cglib.proxy.Dispatcher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthFilter: JwtAuthFilter,
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain? {

        http
            .csrf { it.disable() }
            .cors { corsConfigurer -> corsConfigurer.configure(http) }
            .sessionManagement { managementSession ->
                managementSession.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests {
                it.dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                it.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                it.requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                it.anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}