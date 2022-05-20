package com.example.tmdb.repository

import com.example.tmdb.data.local.*
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class FavouritesRepository @Inject constructor(private val dao: TmdbDao) {

    suspend fun insertFavorite(favorite: Favourite) = dao.insertFavourite(favorite)

    suspend fun insertFavouritesWithCast(join: FavouritesWithCastCrossRef) =
        dao.insertFavouritesWithCast(join)

    suspend fun insertCast(castLocal: CastLocal) = dao.insertCast(castLocal)

    suspend fun insertCrew(crewLocal: CrewLocal) = dao.insertCrew(crewLocal)

    fun getFavorites(): Flow<List<Favourite>> = dao.getAllFavourites()

    fun isFavorite(mediaId: Int): Flow<Boolean> = dao.isFavourite(mediaId)

    suspend fun deleteOneFavorite(mediaId: Int) = dao.deleteFavourite(mediaId)

    suspend fun deleteCast(mediaId: Int) = dao.deleteCast(mediaId)

    suspend fun deleteCrew(mediaId: Int) = dao.deleteCrew(mediaId)

    suspend fun deleteFavouritesWithCastCrossRef(mediaId: Int) =
        dao.deleteFavouritesWithCastCrossRef(mediaId)

    fun getFavouritesWithCast(mediaId: Int): Flow<FavouritesWithCast> =
        dao.getFavouritesWithCast(mediaId)

    fun getFavouritesWithCrew(mediaId: Int): Flow<FavouritesWithCrew> =
        dao.getFavouritesWithCrew(mediaId)
}
