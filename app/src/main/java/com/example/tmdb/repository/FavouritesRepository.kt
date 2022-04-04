package com.example.tmdb.repository

import androidx.lifecycle.LiveData
import com.example.tmdb.data.local.Favourite
import com.example.tmdb.data.local.TmdbDatabase
import javax.inject.Inject

class FavouritesRepository@Inject constructor(private val database: TmdbDatabase) {
    suspend fun insertFavorite(favorite: Favourite) {
        database.dao.insertFavourite(favorite)
    }

    fun getFavorites(): LiveData<List<Favourite>> {
        return database.dao.getAllFavourites()
    }

    fun isFavorite(mediaId: Int): LiveData<Boolean> {
        return database.dao.isFavourite(mediaId)
    }

    fun getAFavorites(mediaId: Int): LiveData<Favourite?> {
        return database.dao.getFavourite(mediaId)
    }

    suspend fun deleteOneFavorite(favorite: Favourite) {
        database.dao.deleteFavourite(favorite)
    }

    suspend fun deleteAllFavorites() {
        database.dao.deleteAllFavourites()
    }
}