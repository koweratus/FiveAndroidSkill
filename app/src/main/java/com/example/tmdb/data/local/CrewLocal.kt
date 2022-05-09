package com.example.tmdb.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tmdb.utils.Constants.CREW_TABLE

@Entity(tableName = CREW_TABLE)
data class CrewLocal(
    val adult: Boolean,
    val job: String,
    val creditId: String,
    val gender: Int,
    @PrimaryKey val id: Int,
    val knownForDepartment: String,
    val department: String,
    val name: String,
    val originalName: String,
    val profilePath: String? = "https://pixy.org/src/9/94083.png",
    val popularity: Double,
    val movieIdForCrew: Int
)