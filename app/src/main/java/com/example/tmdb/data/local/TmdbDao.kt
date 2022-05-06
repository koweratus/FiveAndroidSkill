package com.example.tmdb.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TmdbDao {

    @Insert
    suspend fun insertFavourite(favourite: Favourite)

    @Insert
    suspend fun insertCast(cast: CastLocal)

    @Query("SELECT * FROM cast_table WHERE castId = :mediaId")
    fun getCast(mediaId: Int): Flow<CastLocal?>

    @Query("SELECT * FROM cast_table ORDER BY name DESC")
    fun getAllCast(): Flow<List<CastLocal>>

    @Query("SELECT * FROM tmdb_table ORDER BY mediaId DESC")
    fun getAllFavourites(): Flow<List<Favourite>>

    @Query("SELECT * FROM tmdb_table WHERE mediaId = :mediaId")
    fun getFavourite(mediaId: Int): Flow<Favourite?>

    @Query("SELECT favourite FROM tmdb_table WHERE mediaId = :mediaId")
    fun isFavourite(mediaId: Int): Flow<Boolean>

    @Query("DELETE FROM tmdb_table WHERE mediaId = :mediaId")
    suspend fun deleteFavourite(mediaId: Int)

    @Query("DELETE FROM cast_table WHERE castId = :mediaId")
    suspend fun deleteCast(mediaId: Int)

    @Query("DELETE FROM tmdb_table")
    suspend fun deleteAllFavourites()
}