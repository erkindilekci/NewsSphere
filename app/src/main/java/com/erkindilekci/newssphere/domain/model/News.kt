package com.erkindilekci.newssphere.domain.model

data class News(
    val title: String,
    val content: String?,
    val author: String?,
    val url: String,
    val urlToImage: String,
    val description: String?
)
