package com.example.tmdb.screens.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.filter
import com.example.tmdb.model.Movie
import com.example.tmdb.remote.responses.*
import com.example.tmdb.repository.MovieDetailsRepository
import com.example.tmdb.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: MovieDetailsRepository
) : ViewModel() {

    private var _trendingMoviesDay = mutableStateOf <Flow<PagingData<Movie>>>(emptyFlow())
    val trendingMoviesDay: State<Flow<PagingData<Movie>>> = _trendingMoviesDay


    init{
        getTrendingMoviesDay(null)
    }
    fun getTrendingMoviesDay(genreId: Int?){
        viewModelScope.launch {
            _trendingMoviesDay.value = if(genreId != null){
                repository.getTrendingMoviesWeek().map { pagingData ->
                    pagingData.filter {
                        it.genreIds.contains(genreId)
                    }
                }.cachedIn(viewModelScope)

            }else{
                repository.getTrendingMoviesWeek().cachedIn(viewModelScope)
            }
        }
    }
    suspend fun getMovieDetails(movieId: Int?): Resource<MovieDetails> {
        return repository.getMoviesDetails(movieId)
    }

    suspend fun getTvDetails(tvId: Int?): Resource<TvDetails> {
        return repository.getTvDetails(tvId)
    }

    suspend fun getMovieCasts(movieId: Int): Resource<CreditsResponse> {
        val cast = repository.getMovieCasts(movieId)
        Timber.d(cast.data.toString())
        return cast
    }

    suspend fun getMovieReviews(movieId: Int): Resource<ReviewResponse> {
        val review = repository.getMovieReviews(movieId)
        Timber.d(review.data.toString())
        return review
    }

    suspend fun getMovieRecommendations(movieId: Int): Resource<MoviesResponse> {
        val recommendations = repository.getMovieRecommendations(movieId)
        Timber.d(recommendations.data.toString())
        return recommendations
    }

    suspend fun getTvReviews(movieId: Int): Resource<ReviewResponse> {
        val review = repository.getTvReviews(movieId)
        Timber.d(review.data.toString())
        return review
    }

    suspend fun getTvRecommendations(movieId: Int): Resource<TvResponse> {
        val recommendations = repository.getTVRecommendations(movieId)
        Timber.d(recommendations.data.toString())
        return recommendations
    }
    suspend fun getTvCasts(tvId: Int): Resource<CreditsResponse> {
        return repository.getTvCasts(tvId)
    }
}