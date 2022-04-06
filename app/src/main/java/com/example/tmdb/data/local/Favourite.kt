package com.example.tmdb.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tmdb.utils.Constants.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Favourite(
     val favourite: Boolean,
     @PrimaryKey val mediaId: Int,
     val mediaType: String,
     val image:String,
     val title: String,
     val releaseDate: String,
     val rating: Float
)
