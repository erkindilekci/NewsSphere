package com.erkindilekci.newssphere.presentation.detailscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erkindilekci.newssphere.domain.model.News
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

    private val _new = MutableStateFlow<News?>(null)

    fun getNew(title: String): StateFlow<News?> {
        viewModelScope.launch(Dispatchers.IO) {
            val news = repository.getNew(title)
            _new.value = news
        }
        return _new.asStateFlow()
    }
}    