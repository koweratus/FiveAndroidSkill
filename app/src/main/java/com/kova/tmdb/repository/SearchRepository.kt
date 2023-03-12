package com.example.tmdb.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tmdb.model.Search
import com.example.tmdb.paging.SearchSource
import com.example.tmdb.remote.TMDBApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepository @Inject constructor(private val api: TMDBApi) {

    fun getSearchResults(movie: String): Flow<PagingData<Search>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 27),
            pagingSourceFactory = {
                SearchSource(api, movie)
            }
        ).flow
    }
}
