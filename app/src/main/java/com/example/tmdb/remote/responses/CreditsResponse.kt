package com.example.tmdb.remote.responses

import android.os.Parcelable
import com.example.tmdb.model.Cast
import com.example.tmdb.model.Crew
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreditsResponse(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int
) : Parcelable
