package com.scrumdapp.passporttools.passports

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PassportController(
    private val passportService: PassportService
) {

    @GetMapping("/passport")
    fun getPassport(
        @RequestParam("userId", required = false) userId: Int?,
        @RequestParam("userGroups", required = false) userGroups: List<Int>?,
        @RequestParam("roles", required = false) roles: List<String>?,
        @RequestParam("lifetime", required = false) lifetime: Long?,
    ): JwtResponse {
        return passportService.generateToken(userId, userGroups, roles, lifetime)
    }
}