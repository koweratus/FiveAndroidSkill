package com.example.tmdb.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tmdb.model.Media
import com.example.tmdb.model.Search
import com.example.tmdb.remote.TMDBApi
import retrofit2.HttpException
import java.io.IOException

class SearchSource  (private val api: TMDBApi, private val media: String) :
    PagingSource<Int, Search>() {
    override fun getRefreshKey(state: PagingState<Int, Search>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Search> {
        return try {
            val nextPage = params.key ?: 1
            val searchResults = api.multiSearch(page = nextPage, query = media )
            LoadResult.Page(
                data = searchResults.searches,
                prevKey = if (nextPage == 1) null else nextPage - 1, nextKey = if (searchResults.searches.isEmpty()) null else searchResults.totalResults + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}
