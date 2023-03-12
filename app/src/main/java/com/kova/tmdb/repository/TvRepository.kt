package com.example.tmdb.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tmdb.model.Tv
import com.example.tmdb.paging.AiringTodayTvSource
import com.example.tmdb.paging.OnTheAirTvSource
import com.example.tmdb.paging.PopularTvSource
import com.example.tmdb.paging.TrendingTvSource
import com.example.tmdb.remote.TMDBApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvRepository @Inject constructor(private val api: TMDBApi) {
    fun getTrendingThisWeekTvSeries(): Flow<PagingData<Tv>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 27),
            pagingSourceFactory = {
                TrendingTvSource(api)
            }
        ).flow
    }

    fun getOnTheAirTvSeries(): Flow<PagingData<Tv>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 27),
            pagingSourceFactory = {
                OnTheAirTvSource(api)
            }
        ).flow
    }


    fun getAiringTodayTvSeries(): Flow<PagingData<Tv>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 27),
            pagingSourceFactory = {
                AiringTodayTvSource(api)
            }
        ).flow
    }

    fun getPopularTvSeries(): Flow<PagingData<Tv>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 27),
            pagingSourceFactory = {
                PopularTvSource(api)
            }
        ).flow
    }
}
