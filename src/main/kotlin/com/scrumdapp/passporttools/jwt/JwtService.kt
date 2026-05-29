package com.scrumdapp.passporttools.jwt

import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm
import org.springframework.security.oauth2.jwt.JwsHeader
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class JwtService(
    private val jwtEncoder: JwtEncoder
) {

    fun generateToken(
        subject: String,
        claims: Map<String, Any>,
        lifeTime: Long
    ): String {

        val claimSet = JwtClaimsSet.builder()
            .issuer("mock-gateway")
            .subject(subject)
            .expiresAt(Instant.now().plusSeconds(lifeTime))
            .claims { it.putAll(claims) }
            .build()

        val headers = JwsHeader.with(SignatureAlgorithm.RS256).build()

        return jwtEncoder.encode(
            JwtEncoderParameters.from(headers, claimSet)
        ).tokenValue
    }
}