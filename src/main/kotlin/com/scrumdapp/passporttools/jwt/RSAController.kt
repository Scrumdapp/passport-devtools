package com.scrumdapp.passporttools.jwt

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Base64


@RestController
class RSAController(
    private val rsaKey: RSAKey
) {

    @GetMapping("/.well-known/jwks.json")
    fun publicRsa(): Map<String, Any> {
        return JWKSet(rsaKey).toPublicJWKSet().toJSONObject()
    }

    @GetMapping("/public-rsa")
    fun publicKey(): Map<String, Any> {
        val publicKey = rsaKey.toRSAPublicKey()

        val encoded = Base64.getEncoder().encodeToString(publicKey.encoded)

        val key = "-----BEGIN PUBLIC KEY----- $encoded -----END PUBLIC KEY-----"

        return mapOf("key" to key)
    }
}