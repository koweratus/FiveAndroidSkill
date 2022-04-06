package com.example.tmdb

import com.google.gson.annotations.SerializedName

interface Sucelje {

    @get: SerializedName("poster_path")
    val posterPath: String
}