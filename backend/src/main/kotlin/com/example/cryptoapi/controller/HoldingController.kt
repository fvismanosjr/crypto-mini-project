package com.example.cryptoapi.controller

import com.example.cryptoapi.dto.HoldingRequest
import com.example.cryptoapi.service.HoldingService
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping("/holdings")
class HoldingController(
    private val holdingService: HoldingService
) {
    @GetMapping
    fun findAll() = holdingService.findAll()

    @PostMapping
    fun store(
        @Valid @RequestBody request: HoldingRequest
    ) = holdingService.save(request)
}