package com.example.tmdb.remote

import com.example.tmdb.BuildConfig
import com.example.tmdb.BuildConfig.API_KEY
import com.example.tmdb.remote.responses.*
import com.example.tmdb.utils.Constants.STARTING_PAGE_INDEX
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {

    @GET("trending/movie/day")
    suspend fun getTrendingTodayMovies(
        @Query("page") page: Int = STARTING_PAGE_INDEX,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MoviesResponse

    @GET("trending/movie/week")
    suspend fun getTrendingThisWeekMovies(
        @Query("page") page: Int = STARTING_PAGE_INDEX,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MoviesResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = STARTING_PAGE_INDEX,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MoviesResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int = STARTING_PAGE_INDEX,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MoviesResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = STARTING_PAGE_INDEX,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MoviesResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int = STARTING_PAGE_INDEX,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MoviesResponse


    @GET("movie/{movie_id}/recommendations")
    suspend fun getMovieRecommendations(
        @Path("movie_id") movieId: Int?,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MoviesResponse


    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int?,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MovieDetails

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int?,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): CreditsResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int?,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): ReviewResponse

    @GET("trending/tv/day")
    suspend fun getTrendingTvSeries(
        @Query("page") page: Int = STARTING_PAGE_INDEX,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): TvResponse


    @GET("tv/on_the_air")
    suspend fun getOnTheAirTvSeries(
        @Query("page") page: Int = STARTING_PAGE_INDEX,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): TvResponse

    @GET("tv/popular")
    suspend fun getPopularTvSeries(
        @Query("page") page: Int = STARTING_PAGE_INDEX,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): TvResponse

    @GET("tv/airing_today")
    suspend fun getAiringTodayTvSeries(
        @Query("page") page: Int = STARTING_PAGE_INDEX,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): TvResponse

    @GET("tv/{tv_id}")
    suspend fun getTvSeriesDetails(
        @Path("tv_id") tvSeriesId: Int?,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): TvDetails

    @GET("tv/{tv_id}/recommendations")
    suspend fun getTvRecommendations(
        @Path("tv_id") movieId: Int?,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): TvResponse

    @GET("tv/{tv_id}/credits")
    suspend fun getTvSeriesCredits(
        @Path("tv_id") tvSeriesId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): CreditsResponse

    @GET("tv/{tv_id}/reviews")
    suspend fun getTvSeriesReviews(
        @Path("tv_id") tvSeriesId: Int?,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): ReviewResponse

    @GET("search/multi")
    suspend fun multiSearch(
        @Query("page") page: Int = STARTING_PAGE_INDEX,
        @Query("query") query: String,
        @Query("api_key") apiKey: String = API_KEY
    ): MultiSearchResponse

}