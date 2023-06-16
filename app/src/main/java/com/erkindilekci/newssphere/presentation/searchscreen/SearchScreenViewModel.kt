package com.erkindilekci.newssphere.presentation.searchscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erkindilekci.newssphere.domain.model.NewsResponse
import com.erkindilekci.newssphere.domain.repository.NewsRepository
import com.erkindilekci.newssphere.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    val _searchedNews: MutableStateFlow<Resource<NewsResponse>> =
        MutableStateFlow(Resource.Loading())
    val searchedNews: StateFlow<Resource<NewsResponse>> get() = _searchedNews
    var searchNewsPage = 1


    fun searchNews(searchQuery: String) = viewModelScope.launch(Dispatchers.IO) {
        _searchedNews.value = Resource.Loading()
        val response = repository.searchNews(searchQuery, searchNewsPage)
        _searchedNews.value = handleSearchNewsResponse(response)
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}
