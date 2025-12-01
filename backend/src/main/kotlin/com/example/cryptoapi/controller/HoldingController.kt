package com.example.cryptoapi.controller

import com.example.cryptoapi.dto.HoldingRequest
import com.example.cryptoapi.service.HoldingService
import jakarta.validation.Valid
import org.springframework.data.repository.query.Param
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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

    @GetMapping("/{id}")
    fun findById(
        @PathVariable id: Long
    ) = holdingService.findById(id)

    @PostMapping
    fun store(
        @Valid @RequestBody request: HoldingRequest
    ) = holdingService.save(request)
}