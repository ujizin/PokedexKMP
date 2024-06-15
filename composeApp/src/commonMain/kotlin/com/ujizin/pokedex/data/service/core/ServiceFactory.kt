package com.ujizin.pokedex.data.service.core

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.builtin.FlowConverterFactory
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ServiceFactory {

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }
    val client: Ktorfit
        get() = Ktorfit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .httpClient(httpClient)
            .converterFactories(FlowConverterFactory())
            .build()
}
