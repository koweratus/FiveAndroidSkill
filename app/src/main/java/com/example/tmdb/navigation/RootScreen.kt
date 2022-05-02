package com.example.tmdb.navigation

sealed class RootScreen(
    val route: String,
    val title: String
) {
    object Main : RootScreen(
        route = "main",
        title = "Main"
    )

    object MovieDetails : RootScreen(
        route = "movie_details_screen",
        title = "Movie Details"
    ) {

        const val ARGUMENT_ID = "id"
    }

    object TvDetails : RootScreen(
        route = "tv_details_screen",
        title = "Tv Details"
    ) {

        const val ARGUMENT_ID = "id"
    }
}