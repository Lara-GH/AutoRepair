package org.autorepair.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


val jsonObject = Json {
    useAlternativeNames = false
    prettyPrint = true
    ignoreUnknownKeys = true
}

object Ktor {

    val client = HttpClient {
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    print(message)
                }
            }
        }

        install(ContentNegotiation) {
            json(jsonObject)
        }
    }
}