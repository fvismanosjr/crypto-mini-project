package com.example.cryptoapi.dto

import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class RegisterRequest(
    @field:NotBlank
    val name: String,

    @field:NotBlank
    @field:Email
    val email: String,

    @field:NotBlank
    val password: String,

    @field:NotBlank
    val confirmPassword: String,
) {
    @AssertTrue(message = "Password do not match")
    fun isPasswordMatch(): Boolean {
        return password == confirmPassword
    }
}