package com.example.tmdb.data.local

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation


data class FavouritesWithCast(
    @Embedded val favourite: Favourite,
    @Relation(
        parentColumn = "mediaId",
        entityColumn = "id",
        associateBy = Junction(FavouritesWithCastCrossRef::class)
    )
    val castLocal: List<CastLocal>
)
