package com.raywenderlich.learn.data

import HttpClientLogger
import com.raywenderlich.learn.data.model.GravatarProfile
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlin.native.concurrent.ThreadLocal

import io.ktor.client.plugins.ContentNegotiation
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


public const val GRAVATAR_URL = "https://en.gravatar.com/"
public const val GRAVATAR_RESPONSE_FORMAT = ".json"

@ThreadLocal
public object FeedAPI {

    private val nonStrictJson = Json { isLenient = true; ignoreUnknownKeys = true }
    private val client: HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(nonStrictJson)
        }

        install(Logging) {
            logger = HttpClientLogger
            level = LogLevel.ALL
        }
    }

    public suspend fun fetchRWEntry(feedUrl: String): HttpResponse = client.get(feedUrl)

    public suspend fun fetchMyGravatar(hash: String): GravatarProfile =
        client.get("$GRAVATAR_URL$hash$GRAVATAR_RESPONSE_FORMAT").body()
}



