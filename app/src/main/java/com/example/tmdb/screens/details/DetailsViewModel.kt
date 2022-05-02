package com.example.tmdb.screens.details

import androidx.lifecycle.ViewModel
import com.example.tmdb.remote.responses.*
import com.example.tmdb.repository.MovieDetailsRepository
import com.example.tmdb.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: MovieDetailsRepository
) : ViewModel() {


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