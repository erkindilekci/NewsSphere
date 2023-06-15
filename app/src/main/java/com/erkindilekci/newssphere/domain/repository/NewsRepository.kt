package com.erkindilekci.newssphere.domain.repository

import com.erkindilekci.newssphere.domain.model.News

interface NewsRepository {

    suspend fun getNews(country: String): List<News>
    fun getNew(title: String): News
}
