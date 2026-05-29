package com.scrumdapp.passporttools.passports

import com.scrumdapp.passporttools.jwt.JwtService
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter



data class JwtResponse(
    val token: String,
    val claims: Map<String, Any>,
    val expiresAt: String,
)

@Service
class PassportService(
    private val jwtService: JwtService
) {

    fun generateToken(
        userId: Int?,
        userGroups: List<Int>?,
        roles: List<String>?,
        lifeTime: Long?
    ): JwtResponse {

        val passport = PassportContent(
            userId ?: 1,
            userGroups ?: listOf(1, 2, 3),
            roles ?: listOf("STUDENT")
        )

        val subject = if (userId != null) {
            "$userId" } else { "1" }

        val token = jwtService.generateToken(
            subject,
            passport.toJwtClaim(),
            lifeTime ?: (60 * 5)
        )

        val bearerToken = "Bearer $token"
        val expiry = formatExpiryTime(lifeTime ?: (60 * 5))

        return JwtResponse(bearerToken, passport.toJwtClaim(), expiry)
    }

    private fun formatExpiryTime(lifeTime: Long): String {
        val dateTimeFormatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm")

        return LocalDateTime.now().plusSeconds(lifeTime).format(dateTimeFormatter)
    }
}