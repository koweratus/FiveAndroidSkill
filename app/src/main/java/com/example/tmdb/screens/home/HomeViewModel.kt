package com.example.tmdb.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.tmdb.model.*
import com.example.tmdb.repository.MoviesRepository
import com.example.tmdb.repository.TvRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val tvRepository: TvRepository,
) : ViewModel(){

    // Movie
    private var _trendingMoviesDay = mutableStateOf <Flow<PagingData<Movie>>>(emptyFlow())
    val trendingMoviesDay: State<Flow<PagingData<Movie>>> = _trendingMoviesDay

    private var _trendingMoviesWeek = mutableStateOf <Flow<PagingData<Movie>>>(emptyFlow())
    val trendingMoviesWeek: State<Flow<PagingData<Movie>>> = _trendingMoviesWeek

    private val _upcomingMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val upcomingMovies: State<Flow<PagingData<Movie>>> = _upcomingMovies

    private val _topRatedMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val topRatedMovies: State<Flow<PagingData<Movie>>> = _topRatedMovies

    private val _nowPlayingMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val nowPlayingMovies: State<Flow<PagingData<Movie>>> = _nowPlayingMovies

    private val _popularMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val popularMovies: State<Flow<PagingData<Movie>>> = _popularMovies

    //Tv
    private val _trendingTv = mutableStateOf<Flow<PagingData<Tv>>>(emptyFlow())
    val trendingTv: State<Flow<PagingData<Tv>>> = _trendingTv

    private val _onAirTv = mutableStateOf<Flow<PagingData<Tv>>>(emptyFlow())
    val onAirTv: State<Flow<PagingData<Tv>>> = _onAirTv

    private val _airingTodayTv = mutableStateOf<Flow<PagingData<Tv>>>(emptyFlow())
    val airingTodayTv: State<Flow<PagingData<Tv>>> = _airingTodayTv

    private val _popularTv = mutableStateOf<Flow<PagingData<Tv>>>(emptyFlow())
    val popularTv: State<Flow<PagingData<Tv>>> = _popularTv

    init{
        getTrendingMoviesDay(null)
        getTrendingMoviesWeek(null)
        getNowPayingMovies(null)
        getUpcomingMovies(null)
        getPopularMovies(null)

        getAiringTodayTv(null)
        getTrendingTv(null)
        getOnTheAirTv(null)
        getOnTheAirTv(null)
        getPopularTv(null)
    }

    //Movie
    fun getTrendingMoviesDay(genreId: Int?){
        viewModelScope.launch {
            _trendingMoviesDay.value = if(genreId != null){
                moviesRepository.getTrendingMoviesDay().map { pagingData ->
                    pagingData.filter {
                       it.genreIds.contains(genreId)
                    }
                }.cachedIn(viewModelScope)

            }else{
                moviesRepository.getTrendingMoviesDay().cachedIn(viewModelScope)
            }
        }
    }

    fun getTrendingMoviesWeek(genreId: Int?){
        viewModelScope.launch {
            _trendingMoviesWeek.value = if(genreId != null){
                moviesRepository.getTrendingMoviesWeek().map { pagingData ->
                    pagingData.filter {
                        it.genreIds.contains(genreId)
                    }
                }.cachedIn(viewModelScope)

            }else{
                moviesRepository.getTrendingMoviesWeek().cachedIn(viewModelScope)
            }
        }
    }

    fun getUpcomingMovies(genreId: Int?) {
        viewModelScope.launch {
            _upcomingMovies.value = if (genreId != null) {
                moviesRepository.getUpcomingMovies().map { pagingData ->
                    pagingData.filter {
                        it.genreIds.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                moviesRepository.getUpcomingMovies().cachedIn(viewModelScope)
            }
        }
    }


    fun getNowPayingMovies(genreId: Int?) {
        viewModelScope.launch {
            _nowPlayingMovies.value = if (genreId != null) {
                moviesRepository.getNowPlayingMovies().map { pagingData ->
                    pagingData.filter {
                        it.genreIds.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                moviesRepository.getNowPlayingMovies().cachedIn(viewModelScope)
            }
        }
    }

    fun getPopularMovies(genreId: Int?) {
        viewModelScope.launch {
            _popularMovies.value = if (genreId != null) {
                moviesRepository.getPopularMovies().map { pagingData ->
                    pagingData.filter {
                        it.genreIds.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                moviesRepository.getPopularMovies().cachedIn(viewModelScope)
            }
        }
    }

    // Tv
    fun getTrendingTv(genreId: Int?) {
        viewModelScope.launch {
            _trendingTv.value = if (genreId != null) {
                tvRepository.getTrendingThisWeekTvSeries().map { pagingData ->
                    pagingData.filter {
                        it.genreIds.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                tvRepository.getTrendingThisWeekTvSeries().cachedIn(viewModelScope)
            }
        }
    }

    fun getOnTheAirTv(genreId: Int?) {
        viewModelScope.launch {
            _onAirTv.value = if (genreId != null) {
                tvRepository.getOnTheAirTvSeries().map { pagingData ->
                    pagingData.filter {
                        it.genreIds.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                tvRepository.getOnTheAirTvSeries().cachedIn(viewModelScope)
            }
        }
    }

    fun getAiringTodayTv(genreId: Int?) {
        viewModelScope.launch {
            _airingTodayTv.value = if (genreId != null) {
                tvRepository.getAiringTodayTvSeries().map { pagingData ->
                    pagingData.filter {
                        it.genreIds.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                tvRepository.getAiringTodayTvSeries().cachedIn(viewModelScope)
            }
        }
    }

    fun getPopularTv(genreId: Int?) {
        viewModelScope.launch {
            _popularTv.value = if (genreId != null) {
                tvRepository.getPopularTvSeries().map { pagingData ->
                    pagingData.filter {
                        it.genreIds.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                tvRepository.getPopularTvSeries().cachedIn(viewModelScope)
            }
        }
    }

}