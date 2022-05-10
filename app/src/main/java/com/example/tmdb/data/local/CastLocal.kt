package com.example.tmdb.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tmdb.utils.Constants.CAST_TABLE

@Entity(tableName = CAST_TABLE)
data class CastLocal(
    val adult: Boolean,
    val castId: Int,
    val character: String,
    val creditId: String,
    val gender: Int,
    @PrimaryKey val id: Int,
    val knownForDepartment: String,
    val name: String,
    val order: Int,
    val originalName: String,
    val popularity: Double,
    val profilePath: String? = "https://pixy.org/src/9/94083.png",
    val movieIdForCast: Int
)