package com.example.tmdb.model

import com.google.gson.annotations.SerializedName

data class Tv(
    @SerializedName("origin_country")
    val originCountry: List<String>,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_name")
    val originalName: String,
) : Media()
