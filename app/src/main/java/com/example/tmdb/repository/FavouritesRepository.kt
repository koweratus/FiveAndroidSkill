package com.example.tmdb.repository

import com.example.tmdb.data.local.CastLocal
import com.example.tmdb.data.local.Favourite
import com.example.tmdb.data.local.TmdbDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavouritesRepository @Inject constructor(private val database: TmdbDatabase) {
    suspend fun insertFavorite(favorite: Favourite) {
        database.dao.insertFavourite(favorite)
    }
    suspend fun insertCast(castLocal: CastLocal) {
        database.dao.insertCast(castLocal)
    }
    fun getFavorites(): Flow<List<Favourite>> {
        return database.dao.getAllFavourites()
    }
    fun getCasts(): Flow<List<CastLocal>> {
        return database.dao.getAllCast()
    }

    fun isFavorite(mediaId: Int): Flow<Boolean> {
        return database.dao.isFavourite(mediaId)
    }

    fun getCast(mediaId: Int): Flow<CastLocal?> {
        return database.dao.getCast(mediaId)
    }

    fun getAFavorites(mediaId: Int): Flow<Favourite?> {
        return database.dao.getFavourite(mediaId)
    }

    suspend fun deleteOneFavorite(mediaId: Int) {
        database.dao.deleteFavourite(mediaId)
    }

    suspend fun deleteCast(mediaId: Int) {
        database.dao.deleteCast(mediaId)
    }

    suspend fun deleteAllFavorites() {
        database.dao.deleteAllFavourites()
    }
}