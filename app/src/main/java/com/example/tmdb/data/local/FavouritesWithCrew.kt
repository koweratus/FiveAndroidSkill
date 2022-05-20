package com.example.tmdb.data.local

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class FavouritesWithCrew(
    @Embedded val favourite: Favourite,
    @Relation(
        parentColumn = "mediaId",
        entityColumn = "movieIdForCrew"
    )
    val crewLocal: List<CrewLocal>
)
