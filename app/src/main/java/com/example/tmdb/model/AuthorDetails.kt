package com.example.tmdb.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AuthorDetails(
    @SerializedName("name")
    val name:String,
    @SerializedName("username")
    val username:String,
    @SerializedName("avatar_path")
    val avatarPath: String,
    @SerializedName("rating")
    val rating: Int
):Parcelable
