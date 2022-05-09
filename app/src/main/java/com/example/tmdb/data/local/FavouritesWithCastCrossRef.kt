package com.example.tmdb.data.local

import androidx.room.Entity

@Entity(primaryKeys = ["mediaId", "id"])
data class FavouritesWithCastCrossRef(
    val mediaId: Int,
    val id: Int
)