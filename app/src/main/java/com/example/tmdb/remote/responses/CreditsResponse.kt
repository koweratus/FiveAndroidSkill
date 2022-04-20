package com.example.tmdb.remote.responses

import android.os.Parcelable
import com.example.tmdb.model.Cast
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreditsResponse(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("id")
    val id: Int
) : Parcelable
