package com.example.tmdb.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TmdbDao {

    @Insert
    suspend fun insertFavourite(favourite: Favourite)

    @Insert(onConflict = IGNORE)
    suspend fun insertCast(cast: CastLocal)

    @Insert(onConflict = IGNORE)
    suspend fun insertCrew(crew: CrewLocal)

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

    @Query("DELETE FROM cast_table WHERE movieIdForCast = :mediaId")
    suspend fun deleteCast(mediaId: Int)

    @Query("DELETE FROM crew_table WHERE movieIdForCrew = :mediaId")
    suspend fun deleteCrew(mediaId: Int)

    @Query("DELETE FROM FavouritesWithCastCrossRef WHERE mediaId = :mediaId")
    suspend fun deleteFavouritesWithCastCrossRef(mediaId: Int)

    @Query("DELETE FROM tmdb_table")
    suspend fun deleteAllFavourites()

    @Transaction
    @Query("SELECT * FROM tmdb_table where mediaId = :mediaId")
    fun getFavouritesWithCast(mediaId: Int): Flow<FavouritesWithCast>

    @Transaction
    @Query("SELECT * FROM tmdb_table where mediaId = :mediaId")
    fun getFavouritesWithCrew(mediaId: Int): Flow<FavouritesWithCrew>

    @Insert(onConflict = IGNORE)
    suspend fun insertFavouritesWithCast(join: FavouritesWithCastCrossRef)

}