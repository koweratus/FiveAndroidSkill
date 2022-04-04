package com.example.tmdb.remote.responses

import com.example.tmdb.model.Genre
import com.google.gson.annotations.SerializedName

data class GenreResponse (
    @SerializedName("genres")
    val genres: List<Genre>
        )