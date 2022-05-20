package com.example.tmdb.repository

import com.example.tmdb.remote.TMDBApi
import com.example.tmdb.remote.responses.*
import com.example.tmdb.utils.Resource
import timber.log.Timber
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(private val api: TMDBApi) {

    // Movie Details
    suspend fun getMoviesDetails(movieId: Int?): Resource<MovieDetails> {
        val response = try {
            api.getMovieDetails(movieId)
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Movie details: $response")
        return Resource.Success(response)
    }

    // Movie Casts
    suspend fun getMovieCasts(movieId: Int?): Resource<CreditsResponse> {
        val response = try {
            api.getMovieCredits(movieId)
        } catch (e: Exception) {
            Timber.d(e.message)
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Movie Casts $response")
        return Resource.Success(response)
    }

    //Movie Reviews
    suspend fun getMovieReviews(movieId: Int?): Resource<ReviewResponse> {
        val response = try {
            api.getMovieReviews(movieId)
        } catch (e: Exception) {
            Timber.d(e.message)
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Movie Casts $response")
        return Resource.Success(response)
    }

    //Movie Recommendations

    suspend fun getMovieRecommendations(movieId: Int): Resource<MoviesResponse> {
        val response = try {
            api.getMovieRecommendations(movieId)
        } catch (e: Exception) {
            Timber.d(e.message)
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Movie Casts $response")
        return Resource.Success(response)
    }

    // Tv Details
    suspend fun getTvDetails(tvId: Int?): Resource<TvDetails> {
        val response = try {
            api.getTvSeriesDetails(tvId)
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Series details: $response")
        return Resource.Success(response)
    }

    // Tv Casts
    suspend fun getTvCasts(tvId: Int): Resource<CreditsResponse> {
        val response = try {
            api.getTvSeriesCredits(tvId)
        } catch (e: Exception) {
            Timber.d(e.message)
            return Resource.Error("Unknown error occurred")
        }

        Timber.d("Series casts $response")
        return Resource.Success(response)
    }

    //Movie Reviews
    suspend fun getTvReviews(movieId: Int?): Resource<ReviewResponse> {
        val response = try {
            api.getTvSeriesReviews(movieId)
        } catch (e: Exception) {
            Timber.d(e.message)
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Movie Casts $response")
        return Resource.Success(response)
    }

    //Tv recommendations
    suspend fun getTVRecommendations(movieId: Int): Resource<TvResponse> {
        val response = try {
            api.getTvRecommendations(movieId)
        } catch (e: Exception) {
            Timber.d(e.message)
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Movie Casts $response")
        return Resource.Success(response)
    }
}
