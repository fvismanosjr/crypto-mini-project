package com.example.cryptoapi.service

import com.example.cryptoapi.dto.PortfolioRequest
import com.example.cryptoapi.dto.PortfolioResponse
import com.example.cryptoapi.entity.Portfolio
import com.example.cryptoapi.repository.HoldingRepository
import com.example.cryptoapi.repository.PortfolioRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PortfolioService(
    private val portfolioRepository: PortfolioRepository,
    private val holdingRepository: HoldingRepository
) {
    fun findAll() = portfolioRepository.findAll().map { it.toResponse() }

    fun save(
        request: PortfolioRequest
    ): PortfolioResponse {
        return portfolioRepository.save(Portfolio(
            name = request.name
        )).toResponse()
    }
}