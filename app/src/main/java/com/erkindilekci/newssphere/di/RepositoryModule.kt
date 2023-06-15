package com.erkindilekci.newssphere.di

import com.erkindilekci.newssphere.data.data_soruce.remote.NewsApi
import com.erkindilekci.newssphere.data.repository.NewsRepositoryImpl
import com.erkindilekci.newssphere.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNewsRepository(api: NewsApi): NewsRepository = NewsRepositoryImpl(api)
}
