package com.example.cryptoapi.service

import com.example.cryptoapi.dto.HoldingRequest
import com.example.cryptoapi.dto.HoldingResponse
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
    fun find(id: Long) = portfolioRepository
                            .findById(id)
                            .orElseThrow { Exception("no portfolio found") }

    fun findAll() = portfolioRepository.findAll().map { it.toResponse() }

    fun findById(id: Long): PortfolioResponse = portfolioRepository
                                                .findById(id)
                                                .map { it.toResponse() }
                                                .orElseThrow { Exception("no portfolio found") }

    fun findAllWithHoldings() = portfolioRepository.findAll().map {
        it.toResponseWithHoldings()
    }

    fun save(
        request: PortfolioRequest
    ): PortfolioResponse {
        return portfolioRepository.save(Portfolio(
            name = request.name
        )).toResponse()
    }

    fun edit(
        id: Long,
        request: PortfolioRequest
    ): PortfolioResponse {
        return portfolioRepository
                .save(
                find(id)
                    .apply {
                        this.name = request.name
                    }
                )
                .let { find(it.id).toResponse() }
    }

    fun delete(id: Long) = portfolioRepository.delete(
        find(id)
    )
}