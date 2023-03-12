package com.example.tmdb.model

import com.google.gson.annotations.SerializedName

sealed class Media{
    @SerializedName("backdrop_path")
    val backdropPath: String = ""
    @SerializedName("genre_ids")
    val genreIds: List<Int> = emptyList()
    @SerializedName("id")
    val id: Int = 0
    @SerializedName("overview")
    val overview: String = ""
    @SerializedName("popularity")
    val popularity: Double = 0.0
    @SerializedName("poster_path")
    val posterPath: String = ""
    @SerializedName("title")
    val title: String = ""
    @SerializedName("name")
    val name: String = ""
    @SerializedName("vote_average")
    val voteAverage: Float = 0f
    @SerializedName("vote_count")
    val voteCount: Int = 0
    @SerializedName("first_air_date")
    val tvReleaseDate: String = ""
    @SerializedName("release_date")
    val movieReleaseDate: String = ""
}
