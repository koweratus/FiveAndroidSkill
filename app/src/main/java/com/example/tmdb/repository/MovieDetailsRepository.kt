package com.example.tmdb.repository

import com.example.tmdb.remote.TMDBApi
import com.example.tmdb.remote.responses.CreditsResponse
import com.example.tmdb.remote.responses.MovieDetails
import com.example.tmdb.utils.Resource
import timber.log.Timber
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(private val api: TMDBApi) {

    // Movie Details
    suspend fun getMoviesDetails(movieId: Int): Resource<MovieDetails> {
        val response = try {
            api.getMovieDetails(movieId)
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Movie details: $response")
        return Resource.Success(response)
    }

    // Movie Casts
    suspend fun getMovieCasts(movieId: Int): Resource<CreditsResponse> {
        val response = try {
            api.getMovieCredits(movieId)
        } catch (e: Exception) {
            Timber.d(e.message)
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Movie Casts $response")
        return Resource.Success(response)
    }

}