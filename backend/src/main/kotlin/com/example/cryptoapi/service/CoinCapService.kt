package com.example.cryptoapi.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.annotation.PostConstruct
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class CoinCapService {

    private val mapper = jacksonObjectMapper()
    private val restTemplate = RestTemplate()
    lateinit var apiData: ApiResponse

    @PostConstruct
    fun fetchDataAtStartup() {
        try {
            val url = "https://rest.coincap.io/v3/assets"

            val entity = HttpHeaders().apply {
                set("Authorization", "Bearer bf6d905abe328de75d95bc0fbce87255795d2ae2632e9146d63aa26250a8d5e2")
            }.let { headers -> HttpEntity<String>(headers) }

            val response = restTemplate.exchange(url, HttpMethod.GET, entity, String::class.java)

            response.body
                ?.let { body -> mapper.readValue<ApiResponse>(body) }
                ?.also { apiData = it }
                ?.also { println("API data fetched and stored in memory: ${it.data.size} assets") }

        } catch (e: Exception) {
            println("Failed to fetch API data: ${e.message}")
        }
    }


    fun getAllAssets() = apiData.data
}

data class ApiResponse(
    val timestamp: Long,
    val data: List<Asset>
)

data class Asset(
    val id: String,
    val rank: String,
    val symbol: String,
    val name: String,
    val supply: String,
    val maxSupply: String?,
    val marketCapUsd: String,
    val volumeUsd24Hr: String,
    val priceUsd: String,
    val changePercent24Hr: String,
    val vwap24Hr: String,
    val explorer: String?,
    val tokens: Map<String, Array<String>> // or another structured type if known
)

