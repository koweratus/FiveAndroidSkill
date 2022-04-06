package com.example.tmdb.repository

import com.example.tmdb.remote.TMDBApi
import com.example.tmdb.remote.responses.GenreResponse
import com.example.tmdb.utils.Resource
import timber.log.Timber
import javax.inject.Inject

class GenresRepository @Inject constructor(private val api: TMDBApi) {
    suspend fun getMoviesGenres(): Resource<GenreResponse> {
        val response = try {
            api.getMovieGenres()
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Movies genres: $response")
        return Resource.Success(response)
    }

    suspend fun getSeriesGenres(): Resource<GenreResponse> {
        val response = try {
            api.getTvGenres()
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Series genres: $response")
        return Resource.Success(response)
    }
}
