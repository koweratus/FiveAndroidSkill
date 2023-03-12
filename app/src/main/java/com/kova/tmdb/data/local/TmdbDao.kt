package com.example.tmdb.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Transaction
import com.example.tmdb.utils.Constants.CAST_TABLE
import com.example.tmdb.utils.Constants.CREW_TABLE
import com.example.tmdb.utils.Constants.FAVOURITESWITHCAST_TABLE
import com.example.tmdb.utils.Constants.TABLE_NAME
import dagger.Provides
import kotlinx.coroutines.flow.Flow

@Dao
interface TmdbDao {

    @Insert
    suspend fun insertFavourite(favourite: Favourite)

    @Insert(onConflict = IGNORE)
    suspend fun insertCast(cast: CastLocal)

    @Insert(onConflict = IGNORE)
    suspend fun insertCrew(crew: CrewLocal)

    @Query("SELECT * FROM $CAST_TABLE WHERE castId = :mediaId")
    fun getCast(mediaId: Int): Flow<CastLocal?>

    @Query("SELECT * FROM $CAST_TABLE ORDER BY name DESC")
    fun getAllCast(): Flow<List<CastLocal>>

    @Query("SELECT * FROM $TABLE_NAME ORDER BY mediaId DESC")
    fun getAllFavourites(): Flow<List<Favourite>>

    @Query("SELECT * FROM $TABLE_NAME WHERE mediaId = :mediaId")
    fun getFavourite(mediaId: Int): Flow<Favourite?>

    @Query("SELECT favourite FROM $TABLE_NAME WHERE mediaId = :mediaId")
    fun isFavourite(mediaId: Int): Flow<Boolean>

    @Query("DELETE FROM $TABLE_NAME WHERE mediaId = :mediaId")
    suspend fun deleteFavourite(mediaId: Int)

    @Query("DELETE FROM $CAST_TABLE WHERE movieIdForCast = :mediaId")
    suspend fun deleteCast(mediaId: Int)

    @Query("DELETE FROM $CREW_TABLE WHERE movieIdForCrew = :mediaId")
    suspend fun deleteCrew(mediaId: Int)

    @Query("DELETE FROM $FAVOURITESWITHCAST_TABLE WHERE mediaId = :mediaId")
    suspend fun deleteFavouritesWithCastCrossRef(mediaId: Int)

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAllFavourites()

    @Transaction
    @Query("SELECT * FROM $TABLE_NAME where mediaId = :mediaId")
    fun getFavouritesWithCast(mediaId: Int): Flow<FavouritesWithCast>

    @Transaction
    @Query("SELECT * FROM $TABLE_NAME where mediaId = :mediaId")
    fun getFavouritesWithCrew(mediaId: Int): Flow<FavouritesWithCrew>

    @Insert(onConflict = IGNORE)
    suspend fun insertFavouritesWithCast(join: FavouritesWithCastCrossRef)

}
