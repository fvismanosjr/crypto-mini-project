package com.example.cryptoapi.repository

import com.example.cryptoapi.entity.Portfolio
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface PortfolioRepository: JpaRepository<Portfolio, Long>, JpaSpecificationExecutor<Portfolio>