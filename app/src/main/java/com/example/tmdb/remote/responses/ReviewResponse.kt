package com.example.tmdb.remote.responses

import android.os.Parcelable
import com.example.tmdb.model.Review
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewResponse(
    @SerializedName("results")
    val review: List<Review>,
    @SerializedName("id")
    val id: Int
) : Parcelable
