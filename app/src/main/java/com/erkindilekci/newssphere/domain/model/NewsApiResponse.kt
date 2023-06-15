package com.erkindilekci.newssphere.domain.model

data class NewsApiResponse(
    val status: String? = null,
    val code: String? = null,
    val articles: List<News>? = null
)
