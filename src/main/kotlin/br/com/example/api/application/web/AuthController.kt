package br.com.example.api.application.web

import br.com.example.api.application.extensions.toResponse
import br.com.example.api.application.requests.LoginRequest
import br.com.example.api.application.requests.RegisterUserRequest
import br.com.example.api.application.responses.LoginResponse
import br.com.example.api.application.responses.RegisterUserResponse
import br.com.example.api.domain.services.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/api/auth")
class AuthController(
    private val service: AuthService
) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterUserRequest): ResponseEntity<RegisterUserResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            service.register(request.email, request.password).toResponse()
        )
     }

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(
            LoginResponse(
                service.login(request.email, request.password)
            )
        )
    }
}