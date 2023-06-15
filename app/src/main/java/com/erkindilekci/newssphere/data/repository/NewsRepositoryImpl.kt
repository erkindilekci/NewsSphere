package com.erkindilekci.newssphere.data.repository

import com.erkindilekci.newssphere.data.data_soruce.local.ArticleDatabase
import com.erkindilekci.newssphere.data.data_soruce.remote.NewsApi
import com.erkindilekci.newssphere.domain.model.Article
import com.erkindilekci.newssphere.domain.model.NewsResponse
import com.erkindilekci.newssphere.domain.repository.NewsRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi,
    private val db: ArticleDatabase
) : NewsRepository {

    private var news: List<Article> = emptyList()

    override suspend fun getBreakingNews(
        countryCode: String,
        pageNumber: Int
    ): Response<NewsResponse> {
        return try {
            val response = api.getBreakingNews(countryCode, pageNumber)
            news = response.body()?.articles ?: emptyList()
            response
        } catch (e: Exception) {
            val errorMessage = e.localizedMessage ?: "Unknown error occurred"
            Response.error(404, ResponseBody.create("text/plain".toMediaTypeOrNull(), errorMessage))
        }
    }

    override suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        api.searchForNews(searchQuery, pageNumber)

    override suspend fun getNews(title: String): Article =
        news.first { it.title == title }
}
