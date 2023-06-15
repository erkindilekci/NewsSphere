package com.erkindilekci.newssphere.domain.repository

import com.erkindilekci.newssphere.domain.model.Article
import com.erkindilekci.newssphere.domain.model.NewsResponse
import retrofit2.Response

interface NewsRepository {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse>
    suspend fun searchNews(searchQuery: String, pageNumber: Int): Response<NewsResponse>
    suspend fun getNews(title: String): Article
}
