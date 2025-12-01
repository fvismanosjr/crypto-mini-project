package com.example.cryptoapi.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class HoldingRequest(
    @field:NotBlank
    val asset: String,

    @field:NotNull
    @field:Min(1)
    val amount: Long,

    @field:NotNull
    val portfolioId: Long?,

    @field:NotNull
    val stock: BigDecimal,
)

data class HoldingResponse(
    val id: Long,
    val asset: String,
    val amount: BigDecimal,
    val portfolio: PortfolioResponse
)