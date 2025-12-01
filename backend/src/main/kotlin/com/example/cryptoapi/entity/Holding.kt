package com.example.cryptoapi.entity

import com.example.cryptoapi.dto.HoldingRequest
import com.example.cryptoapi.dto.HoldingResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "holdings")
class Holding(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    var portfolio: Portfolio,

    @Column(name = "asset")
    var asset: String,

    @Column(name = "amount", precision = 60, scale = 30)
    var amount: BigDecimal,
) {
    fun toResponse(): HoldingResponse {
        return HoldingResponse(
            this.id,
            this.asset,
            this.amount,
            this.portfolio.toResponse()
        )
    }
}