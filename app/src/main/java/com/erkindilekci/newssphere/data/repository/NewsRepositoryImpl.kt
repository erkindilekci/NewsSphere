package com.erkindilekci.newssphere.data.repository

import com.erkindilekci.newssphere.data.data_soruce.remote.NewsApi
import com.erkindilekci.newssphere.domain.model.News
import com.erkindilekci.newssphere.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi
) : NewsRepository {

    private var news: List<News> = emptyList()

    override suspend fun getNews(country: String): List<News> {
        val apiResponse = api.topHeadLines(country).body()
        if (apiResponse?.status == "error") {
            when (apiResponse.code) {
                "apiKeyMissing" -> throw MissingApiKeyException()
                "apiKeyInvalid" -> throw ApiKeyInvalidException()
                else -> throw Exception()
            }
        }
        news = apiResponse?.articles ?: emptyList()
        return news
    }

    override fun getNew(title: String): News =
        news.first { it.title == title }
}

class MissingApiKeyException : java.lang.Exception()
class ApiKeyInvalidException : java.lang.Exception()