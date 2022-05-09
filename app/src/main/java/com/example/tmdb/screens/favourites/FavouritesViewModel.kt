package com.example.tmdb.screens.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.local.*
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

    fun insertCast(castLocal: CastLocal) {
        viewModelScope.launch {
            repository.insertCast(castLocal)
        }
    }

    fun insertCrew(crewLocal: CrewLocal) {
        viewModelScope.launch {
            repository.insertCrew(crewLocal)
        }
    }

    fun insertFavouritesWithCast(join: FavouritesWithCastCrossRef) {
        viewModelScope.launch {
            repository.insertFavouritesWithCast(join)
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

    fun deleteCast(mediaId: Int) {
        viewModelScope.launch {
            repository.deleteCast(mediaId)
        }
    }

    fun deleteCrew(mediaId: Int) {
        viewModelScope.launch {
            repository.deleteCrew(mediaId)
        }
    }

    fun deleteFavouritesWithCastCrossRef(mediaId: Int) {
        viewModelScope.launch {
            repository.deleteFavouritesWithCastCrossRef(mediaId)
        }
    }

    fun getFavouritesWithCast(mediaId: Int): Flow<FavouritesWithCast> {
        return repository.getFavouritesWithCast(mediaId)
    }

    fun getFavouritesWithCrew(mediaId: Int): Flow<FavouritesWithCrew> {
        return repository.getFavouritesWithCrew(mediaId)
    }
}