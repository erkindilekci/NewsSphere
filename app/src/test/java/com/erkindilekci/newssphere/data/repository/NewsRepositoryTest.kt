package com.erkindilekci.newssphere.data.repository

import com.erkindilekci.newssphere.data.data_soruce.remote.NewsApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets

class NewsRepositoryTest {
    private val mockWebServer = MockWebServer()

    private val newsApi = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsApi::class.java)

    private val newsRepository = NewsRepositoryImpl(newsApi)

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Top headlines response is correct`() {
        mockWebServer.enqueueResponse("top_headlines.json")

        runBlocking {
            val articles = newsRepository.getNews("US")
            assertEquals(2, articles.size)
            assertEquals("Sophie Lewis", articles[0].author)
            assertEquals("KOCO Staff", articles[1].author)
        }
    }

    @Test
    fun `Api key missing exception`() {
        mockWebServer.enqueueResponse("api_key_missing.json")
        assertThrows(MissingApiKeyException::class.java) {
            runBlocking {
                newsRepository.getNews("US")
            }
        }
    }

    @Test
    fun `Invalid api key exception`() {
        mockWebServer.enqueueResponse("api_key_invalid.json")
        assertThrows(ApiKeyInvalidException::class.java) {
            runBlocking {
                newsRepository.getNews("US")
            }
        }
    }

}

fun MockWebServer.enqueueResponse(filePath: String) {
    val inputStream = javaClass.classLoader?.getResourceAsStream(filePath)
    val source = inputStream?.source()?.buffer()
    source?.let {
        enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(it.readString(StandardCharsets.UTF_8))
        )
    }
}