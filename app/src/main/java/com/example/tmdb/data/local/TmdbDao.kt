package com.example.tmdb.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TmdbDao {

    @Insert
    suspend fun insertFavourite(favourite: Favourite)

    @Query("SELECT * FROM tmdb_table ORDER BY mediaId DESC")
    fun getAllFavourites(): Flow<List<Favourite>>

    @Query("SELECT * FROM tmdb_table WHERE mediaId = :mediaId")
    fun getFavourite(mediaId: Int): Flow<Favourite?>

    @Query("SELECT favourite FROM tmdb_table WHERE mediaId = :mediaId")
    fun isFavourite(mediaId: Int): Flow<Boolean>

    @Delete
    suspend fun deleteFavourite(favourite: Favourite)

    @Query("DELETE FROM tmdb_table")
    suspend fun deleteAllFavourites()
}