package com.example.tmdb.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tmdb.model.Tv
import com.example.tmdb.remote.TMDBApi
import retrofit2.HttpException
import java.io.IOException

class AiringTodayTvSource (private val api: TMDBApi) :
    PagingSource<Int, Tv>() {
    override fun getRefreshKey(state: PagingState<Int, Tv>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Tv> {
        return try {
            val nextPage = params.key ?: 1
            val airingMoviesToday = api.getAiringTodayTvSeries(nextPage)
            LoadResult.Page(
                data = airingMoviesToday.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (airingMoviesToday.results.isEmpty()) null else airingMoviesToday.page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}