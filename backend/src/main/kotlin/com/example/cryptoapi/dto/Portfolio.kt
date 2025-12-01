package com.example.cryptoapi.dto

import com.example.cryptoapi.entity.Holding
import jakarta.validation.constraints.NotBlank

data class PortfolioRequest(
    @field:NotBlank
    val name: String
)

data class PortfolioResponse(
    val id: Long,
    val name: String,
)

data class PortfolioResponseWithHolding(
    val id: Long,
    val name: String,
    val holdings: List<HoldingResponse>
)