package com.erkindilekci.newssphere.di

import com.erkindilekci.newssphere.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

//@Module
//@TestInstallIn(
//    components = [SingletonComponent::class],
//    replaces = [RepositoryModule::class]
//)
//class TestRepositoryModule {
//
//    @Provides
//    @Singleton
//    fun provideNewsRepository(): NewsRepository {
//        return object : NewsRepository {
//            val news = arrayListOf(
//                News("Title1", "Content1", "Author1", "Url1", "urlImage1", "Description1"),
//                News("Title2", "Content2", "Author2", "Url2", "urlImage2", "Description2")
//            )
//
//            override suspend fun getNews(country: String): List<News> = news
//
//            override fun getNew(title: String): News = news[0]
//
//        }
//    }
//}
