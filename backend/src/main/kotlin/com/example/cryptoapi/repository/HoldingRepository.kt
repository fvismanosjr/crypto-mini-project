package com.example.cryptoapi.repository

import com.example.cryptoapi.entity.Holding
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface HoldingRepository: JpaRepository<Holding, Long>, JpaSpecificationExecutor<Holding>