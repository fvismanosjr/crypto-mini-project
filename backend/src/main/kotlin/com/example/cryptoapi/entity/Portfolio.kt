package com.example.cryptoapi.entity

import com.example.cryptoapi.dto.PortfolioResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "portfolios")
class Portfolio(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "name")
    var name: String,

    @OneToMany(mappedBy = "portfolio", fetch = FetchType.LAZY)
    var holdings: MutableList<Holding> = mutableListOf(),
) {
    fun toResponse(): PortfolioResponse {
        return PortfolioResponse(
            this.id,
            this.name
        )
    }
}