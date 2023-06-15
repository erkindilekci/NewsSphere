package com.erkindilekci.newssphere.presentation.detailscreen

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
    private val repository: NewsRepository
) : ViewModel() {

    private val _new = MutableStateFlow<Article?>(null)

    fun getNew(title: String): StateFlow<Article?> {
        viewModelScope.launch(Dispatchers.IO) {
            val news = repository.getNews(title)
            _new.value = news
        }
        return _new.asStateFlow()
    }
}    