package com.erkindilekci.newssphere.presentation.listscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erkindilekci.newssphere.domain.model.News
import com.erkindilekci.newssphere.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _news = MutableStateFlow<List<News>>(emptyList())

    fun getNews(): StateFlow<List<News>> {
        viewModelScope.launch(Dispatchers.IO) {
            val news = repository.getNews("US")
            _news.value = news
        }
        return _news
    }
}