package br.com.example.api.domain.services

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*


@Service
class TokenServiceImp(
    @param:Value("\${spring.security.auth.secret}")
    private val secret: String? = null,
    @param:Value("\${spring.security.auth.issuer}")
    private val issuer: String? = null
) : TokenService {

   val algorithm: Algorithm? = Algorithm.HMAC256(secret)

    override fun generateToken(userDetails: UserDetails): String {
        try {
            return JWT.create()
                .withIssuer(issuer)
                .withSubject(userDetails.username)
                .withExpiresAt(Date.from(genExpirationDate()))
                .sign(algorithm)
        } catch (exception: JWTCreationException) {
            throw RuntimeException("Token generation error", exception)
        }
    }

    override fun validateToken(token: String?): DecodedJWT {
        try {
            val verifier: JWTVerifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
            return verifier.verify(token)
        } catch (exception: JWTVerificationException) {
            throw java.lang.RuntimeException("Invalid token", exception)
        }
    }

    private fun genExpirationDate(): Instant {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"))
    }
}