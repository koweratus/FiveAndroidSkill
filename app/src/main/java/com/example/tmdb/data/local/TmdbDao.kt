package com.example.tmdb.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TmdbDao {

    @Insert
    suspend fun insertFavourite(favourite: Favourite)

    @Query("SELECT * FROM tmdb_table ORDER BY mediaId DESC")
    fun getAllFavourites(): LiveData<List<Favourite>>

    @Query("SELECT * FROM tmdb_table WHERE mediaId = :mediaId")
    fun getFavourite(mediaId: Int): LiveData<Favourite?>

    @Query("SELECT favourite FROM tmdb_table WHERE mediaId = :mediaId")
    fun isFavourite(mediaId: Int):LiveData<Boolean>

    @Delete
    suspend fun deleteFavourite(favourite: Favourite)

    @Query("DELETE FROM tmdb_table")
    suspend fun deleteAllFavourites()
}