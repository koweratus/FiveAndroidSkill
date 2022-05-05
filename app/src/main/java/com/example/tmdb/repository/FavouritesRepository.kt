package com.example.tmdb.repository

import com.example.tmdb.data.local.Favourite
import com.example.tmdb.data.local.TmdbDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavouritesRepository @Inject constructor(private val database: TmdbDatabase) {
    suspend fun insertFavorite(favorite: Favourite) {
        database.dao.insertFavourite(favorite)
    }

    fun getFavorites(): Flow<List<Favourite>> {
        return database.dao.getAllFavourites()
    }

    fun isFavorite(mediaId: Int): Flow<Boolean> {
        return database.dao.isFavourite(mediaId)
    }

    fun getAFavorites(mediaId: Int): Flow<Favourite?> {
        return database.dao.getFavourite(mediaId)
    }

    suspend fun deleteOneFavorite(mediaId: Int) {
        database.dao.deleteFavourite(mediaId)
    }

    suspend fun deleteAllFavorites() {
        database.dao.deleteAllFavourites()
    }
}