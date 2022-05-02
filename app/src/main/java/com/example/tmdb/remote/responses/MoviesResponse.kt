package com.example.tmdb.remote.responses

import com.google.gson.annotations.SerializedName

data class MoviesResponse(

    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val searches: List<com.example.tmdb.model.Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)