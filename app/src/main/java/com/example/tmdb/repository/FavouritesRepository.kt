package com.example.tmdb.repository

import com.example.tmdb.data.local.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavouritesRepository @Inject constructor(private val database: TmdbDatabase) {

    suspend fun insertFavorite(favorite: Favourite) {
        database.dao.insertFavourite(favorite)
    }

    suspend fun insertFavouritesWithCast(join: FavouritesWithCastCrossRef) {
        database.dao.insertFavouritesWithCast(join)
    }

    suspend fun insertCast(castLocal: CastLocal) {
        database.dao.insertCast(castLocal)
    }

    suspend fun insertCrew(crewLocal: CrewLocal) {
        database.dao.insertCrew(crewLocal)
    }

    fun getFavorites(): Flow<List<Favourite>> {
        return database.dao.getAllFavourites()
    }

     fun isFavorite(mediaId: Int): Flow<Boolean> {
        return database.dao.isFavourite(mediaId)
    }


    suspend fun deleteOneFavorite(mediaId: Int) {
        database.dao.deleteFavourite(mediaId)
    }

    suspend fun deleteCast(mediaId: Int) {
        database.dao.deleteCast(mediaId)
    }

    suspend fun deleteCrew(mediaId: Int) {
        database.dao.deleteCrew(mediaId)
    }

    suspend fun deleteFavouritesWithCastCrossRef(mediaId: Int) {
        database.dao.deleteFavouritesWithCastCrossRef(mediaId)
    }

    fun getFavouritesWithCast(mediaId: Int): Flow<FavouritesWithCast> {
        return database.dao.getFavouritesWithCast(mediaId)
    }

    fun getFavouritesWithCrew(mediaId: Int): Flow<FavouritesWithCrew> {
        return database.dao.getFavouritesWithCrew(mediaId)
    }
}