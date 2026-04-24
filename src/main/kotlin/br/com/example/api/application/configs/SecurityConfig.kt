package br.com.example.api.application.configs

import br.com.example.api.application.filters.ApiKeyAuthFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.util.Collections


//@Configuration
//@EnableWebSecurity
//class SecurityConfig(
//    private val apiKeyFilter: ApiKeyAuthFilter
//) {
//
//    @Bean
//    fun filterChain(http: HttpSecurity): SecurityFilterChain {
//        return http
//            .csrf { it.disable() }
//            .sessionManagement { it.disable() }
//            .authorizeHttpRequests {
//                it.anyRequest().authenticated()
//            }
//            .addFilterBefore(apiKeyFilter, AnonymousAuthenticationFilter::class.java)
//            .build()
//    }
//}

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain? {
        val filter = ApiKeyAuthFilter(AntPathRequestMatcher("/api/**"))
        filter.setAuthenticationManager(AuthenticationManager { authentication: Authentication? ->
            val apiKey = authentication!!.principal as String?
            val apiSecret = authentication.credentials as String?

            if ("my-secret" == apiKey && "my-secret" == apiSecret) {
                    val token = ApiKeyAuthenticationToken(
                    apiKey,
                    apiSecret,
                    Collections.singletonList(SimpleGrantedAuthority("USER"))
                )
                return@AuthenticationManager token
            } else {
                authentication.isAuthenticated = false
            }
            authentication
        })

        http
            .csrf { it.disable() }
            .sessionManagement { managementSession ->
                managementSession.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests {
                it.anyRequest().authenticated()
            }
            .addFilterBefore(filter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}