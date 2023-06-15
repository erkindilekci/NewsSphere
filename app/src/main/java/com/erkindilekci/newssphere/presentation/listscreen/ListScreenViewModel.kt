package com.erkindilekci.newssphere.presentation.listscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erkindilekci.newssphere.domain.model.NewsResponse
import com.erkindilekci.newssphere.domain.repository.NewsRepository
import com.erkindilekci.newssphere.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    val breakingNews: MutableStateFlow<Resource<NewsResponse>> =
        MutableStateFlow(Resource.Loading())
    var breakingNewsPage = 1

    init {
        getBreakingNews("US")
    }

    private fun getBreakingNews(countryCode: String) = viewModelScope.launch(Dispatchers.IO) {
        breakingNews.value = Resource.Loading()
        val response = repository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.value = handleBreakingNewsResponse(response)
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}
