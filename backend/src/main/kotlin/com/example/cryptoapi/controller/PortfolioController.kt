package com.example.cryptoapi.controller

import com.example.cryptoapi.dto.PortfolioRequest
import com.example.cryptoapi.service.CoinCapService
import com.example.cryptoapi.service.PortfolioService
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping("/portfolios")
class PortfolioController(
    private val coinCapService: CoinCapService,
    private val portfolioService: PortfolioService
) {

    @GetMapping
    fun findAll() = portfolioService.findAll()

    @GetMapping("/{id}")
    fun findById(
        @PathVariable id: Long
    ) = portfolioService.findById(id)

    @GetMapping("/with-holdings")
    fun findAllWithHoldings() = portfolioService.findAllWithHoldings()

    @PostMapping
    fun store(
        @Valid @RequestBody request: PortfolioRequest
    ) = portfolioService.save(request)

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody request: PortfolioRequest
    ) = portfolioService.edit(id, request)

    @DeleteMapping("/{id}")
    fun destroy(
        @PathVariable id: Long
    ) = portfolioService.delete(id)
}