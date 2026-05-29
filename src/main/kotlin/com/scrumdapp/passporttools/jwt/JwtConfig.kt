package com.scrumdapp.passporttools.jwt

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder

@Configuration
class JwtConfig {


    @Bean
    fun rsaKey(): RSAKey {
        return RSAKeyGenerator(2048).keyID("mock-gateway").generate()
    }

    @Bean
    fun generateJwtEncoder(
        rsaKey: RSAKey
    ): JwtEncoder {
        val jwkSet = JWKSet(rsaKey)
        return NimbusJwtEncoder(ImmutableJWKSet(jwkSet))
    }
}