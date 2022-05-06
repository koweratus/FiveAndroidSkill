package com.example.tmdb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Favourite::class, CastLocal::class), version = 4)
abstract class TmdbDatabase : RoomDatabase() {
    abstract val dao: TmdbDao
}
