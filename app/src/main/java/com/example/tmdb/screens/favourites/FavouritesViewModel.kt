package com.example.tmdb.screens.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun insertFavorite(favorite: Favourite) {
        viewModelScope.launch {
            repository.insertFavorite(favorite)
        }
    }

    fun isAFavorite(mediaId: Int): Flow<Boolean> {
        return repository.isFavorite(mediaId)
    }

    fun deleteOneFavorite(mediaId: Int) {
        viewModelScope.launch {
            repository.deleteOneFavorite(mediaId)
        }
    }

    fun deleteAllFavorites() {
        viewModelScope.launch {
            repository.deleteAllFavorites()
        }
    }
}