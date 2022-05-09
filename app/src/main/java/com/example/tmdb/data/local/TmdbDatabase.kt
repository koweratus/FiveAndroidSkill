package com.example.tmdb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Favourite::class, CastLocal::class, CrewLocal::class, FavouritesWithCastCrossRef::class), version = 6)
abstract class TmdbDatabase : RoomDatabase() {
    abstract val dao: TmdbDao
}
