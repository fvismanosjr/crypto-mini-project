package com.example.cryptoapi.controller

import com.example.cryptoapi.service.CoinCapService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/assets")
class AssetController(
    private val coinCapService: CoinCapService
) {
    @GetMapping
    fun findAll() = coinCapService.getAllAssets()
}