package com.example.cryptoapi.service

import com.example.cryptoapi.dto.HoldingRequest
import com.example.cryptoapi.dto.HoldingResponse
import com.example.cryptoapi.entity.Holding
import com.example.cryptoapi.repository.HoldingRepository
import com.example.cryptoapi.repository.PortfolioRepository
import org.springframework.stereotype.Service

@Service
class HoldingService(
    private val portfolioRepository: PortfolioRepository,
    private val holdingRepository: HoldingRepository
) {
    fun findAll() = holdingRepository.findAll().map { it.toResponse() }

    fun save(
        request: HoldingRequest
    ): HoldingResponse {
        val portfolio = portfolioRepository
            .findById(request.portfolioId!!)
            .orElseThrow{ Exception("portfolio not found") }

        val holding = holdingRepository.save(
            Holding(
                portfolio = portfolio,
                asset = request.asset,
                amount = request.stock
            )
        )

        return holding.toResponse()
    }
}