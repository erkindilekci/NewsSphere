package com.erkindilekci.newssphere.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.erkindilekci.newssphere.data.data_soruce.remote.NewsApi
import com.erkindilekci.newssphere.domain.model.Article
import java.io.IOException
import javax.inject.Inject

class NewsPagingSource @Inject constructor(
    private val api: NewsApi
) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: 0
            val response = api.getBreakingNews(pageNumber = page)

            if (response.isSuccessful) {
                val body = response.body()?.articles ?: emptyList()
                LoadResult.Page(
                    data = body,
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (body.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(IOException())
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
