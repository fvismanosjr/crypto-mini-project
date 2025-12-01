package com.example.cryptoapi.controller

import com.example.cryptoapi.dto.PortfolioRequest
import com.example.cryptoapi.service.CoinCapService
import com.example.cryptoapi.service.PortfolioService
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
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

    @PostMapping
    fun store(
        @Valid @RequestBody request: PortfolioRequest
    ) = portfolioService.save(request)
}