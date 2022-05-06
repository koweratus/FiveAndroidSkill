package com.example.tmdb.screens.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.local.CastLocal
import com.example.tmdb.data.local.Favourite
import com.example.tmdb.repository.FavouritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(private val repository: FavouritesRepository) :
    ViewModel() {

    val favourites = repository.getFavorites()

    val casts = repository.getCasts()

    fun insertFavorite(favorite: Favourite) {
        viewModelScope.launch {
            repository.insertFavorite(favorite)
        }
    }

    fun insertCast(castLocal: CastLocal) {
        viewModelScope.launch {
            repository.insertCast(castLocal)
        }
    }

    fun isAFavorite(mediaId: Int): Flow<Boolean> {
        return repository.isFavorite(mediaId)
    }

    fun getCast(mediaId: Int): Flow<CastLocal?> {
        return repository.getCast(mediaId)
    }

    fun getAFavorites(mediaId: Int): Flow<Favourite?> {
        return repository.getAFavorites(mediaId)
    }

    fun deleteOneFavorite(mediaId: Int) {
        viewModelScope.launch {
            repository.deleteOneFavorite(mediaId)
        }
    }

    fun deleteCast(mediaId: Int) {
        viewModelScope.launch {
            repository.deleteCast(mediaId)
        }
    }

    fun deleteAllFavorites() {
        viewModelScope.launch {
            repository.deleteAllFavorites()
        }
    }
}