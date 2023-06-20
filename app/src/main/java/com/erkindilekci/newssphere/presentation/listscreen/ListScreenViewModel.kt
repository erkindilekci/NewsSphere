package com.erkindilekci.newssphere.presentation.listscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.erkindilekci.newssphere.data.data_soruce.remote.NewsApi
import com.erkindilekci.newssphere.data.paging.NewsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val api: NewsApi
) : ViewModel() {

    val pager = Pager(
        config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 30,
            prefetchDistance = 5
        ),
        pagingSourceFactory = { NewsPagingSource(api) }
    ).flow.cachedIn(viewModelScope)
}
