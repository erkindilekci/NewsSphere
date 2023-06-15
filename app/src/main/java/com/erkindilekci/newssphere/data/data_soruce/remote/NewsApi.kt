package com.erkindilekci.newssphere.data.data_soruce.remote

import com.erkindilekci.newssphere.BuildConfig
import com.erkindilekci.newssphere.domain.model.NewsApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines?apiKey=${BuildConfig.API_KEY}")
    suspend fun topHeadLines(
        @Query("country") country: String
    ): Response<NewsApiResponse>
}
