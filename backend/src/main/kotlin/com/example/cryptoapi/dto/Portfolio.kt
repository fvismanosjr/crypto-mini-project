package com.example.cryptoapi.dto

import jakarta.validation.constraints.NotBlank

data class PortfolioRequest(
    @field:NotBlank
    val name: String
)

data class PortfolioResponse(
    val id: Long,
    val name: String,
)