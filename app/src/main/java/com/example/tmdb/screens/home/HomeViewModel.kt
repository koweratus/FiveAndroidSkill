package com.example.tmdb.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tmdb.model.Movie
import com.example.tmdb.model.Tv
import com.example.tmdb.repository.MoviesRepository
import com.example.tmdb.repository.TvRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val tvRepository: TvRepository,
) : ViewModel() {

    // Movie
    private var _trendingMoviesDay: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(PagingData.empty())

    val trendingMoviesDay: Flow<PagingData<Movie>> = _trendingMoviesDay

    private var _trendingMoviesWeek: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(PagingData.empty())
    val trendingMoviesWeek: Flow<PagingData<Movie>> = _trendingMoviesWeek

    private val _upcomingMovies: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(PagingData.empty())
    val upcomingMovies: Flow<PagingData<Movie>> = _upcomingMovies

    private val _nowPlayingMovies: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(PagingData.empty())
    val nowPlayingMovies: Flow<PagingData<Movie>> = _nowPlayingMovies

    private val _popularMovies: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(PagingData.empty())
    val popularMovies: Flow<PagingData<Movie>> = _popularMovies

    //Tv
    private val _onAirTv: MutableStateFlow<PagingData<Tv>> = MutableStateFlow(PagingData.empty())
    val onAirTv: Flow<PagingData<Tv>> = _onAirTv

    private val _popularTv: MutableStateFlow<PagingData<Tv>> = MutableStateFlow(PagingData.empty())
    val popularTv: Flow<PagingData<Tv>> = _popularTv

    init {
        initTrendingMoviesDay()
        initTrendingMoviesWeek()
        initNowPayingMovies()
        initUpcomingMovies()
        initPopularMovies()

        initOnTheAirTv()
        initPopularTv()
    }

    //Movie
    private fun initTrendingMoviesDay() {
        viewModelScope.launch {
            //not sure if it should be .first(); but it works :)
            _trendingMoviesDay.value =
                moviesRepository.getTrendingMoviesDay().cachedIn(viewModelScope).first()
        }
    }

    private fun initTrendingMoviesWeek() {
        viewModelScope.launch {
            _trendingMoviesWeek.value =
                moviesRepository.getTrendingMoviesWeek().cachedIn(viewModelScope).first()

        }
    }

    private fun initUpcomingMovies() {
        viewModelScope.launch {
            _upcomingMovies.value =
                moviesRepository.getUpcomingMovies().cachedIn(viewModelScope).first()

        }
    }


    private fun initNowPayingMovies() {
        viewModelScope.launch {
            _nowPlayingMovies.value =
                moviesRepository.getNowPlayingMovies().cachedIn(viewModelScope).first()

        }
    }

    private fun initPopularMovies() {
        viewModelScope.launch {
            _popularMovies.value =
                moviesRepository.getPopularMovies().cachedIn(viewModelScope).first()

        }
    }

    // Tv
    private fun initOnTheAirTv() {
        viewModelScope.launch {
            _onAirTv.value = tvRepository.getOnTheAirTvSeries().cachedIn(viewModelScope).first()

        }
    }


    private fun initPopularTv() {
        viewModelScope.launch {
            _popularTv.value = tvRepository.getPopularTvSeries().cachedIn(viewModelScope).first()

        }
    }
}
