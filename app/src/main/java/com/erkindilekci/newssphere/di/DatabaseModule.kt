package com.erkindilekci.newssphere.di

import android.content.Context
import androidx.room.Room
import com.erkindilekci.newssphere.data.data_soruce.local.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideArticleDatabase(
        @ApplicationContext context: Context
    ): ArticleDatabase = Room.databaseBuilder(
        context,
        ArticleDatabase::class.java,
        "article_db.db"
    ).build()
}