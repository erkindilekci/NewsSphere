package com.erkindilekci.newssphere.presentation.detailscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erkindilekci.newssphere.domain.model.Article
import com.erkindilekci.newssphere.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val repository: NewsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val new = MutableStateFlow<Article?>(null)

    init {
        savedStateHandle.get<String>("newTitle")?.let { title ->
            getNew(title)
        }
    }

    fun getNew(title: String): StateFlow<Article?> {
        viewModelScope.launch(Dispatchers.IO) {
            val news = repository.getNews(title)
            new.value = news
        }
        return new.asStateFlow()
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.insertArticle(article)
    }
}
