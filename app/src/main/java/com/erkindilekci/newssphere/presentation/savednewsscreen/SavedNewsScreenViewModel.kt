package com.erkindilekci.newssphere.presentation.savednewsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erkindilekci.newssphere.domain.model.Article
import com.erkindilekci.newssphere.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedNewsScreenViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    val savedNews = repository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.deleteArticle(article)
    }
}
