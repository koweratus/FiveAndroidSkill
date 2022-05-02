package com.example.tmdb

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tmdb.navigation.RootScreen
import com.example.tmdb.screens.MovieDetailsScreen
import com.example.tmdb.screens.TvDetailsScreen
import com.google.accompanist.pager.ExperimentalPagerApi


@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun RootNavigationGraph(
    rootNavHostController: NavHostController
) {
    NavHost(rootNavHostController, startDestination = RootScreen.Main.route) {
        composable(RootScreen.Main.route) {
            MainScren(rootNavHostController)
        }
        composable(
            route = RootScreen.MovieDetails.route + "/{${RootScreen.MovieDetails.ARGUMENT_ID}}",
            arguments = listOf(navArgument(RootScreen.MovieDetails.ARGUMENT_ID) {
                type = NavType.IntType
            })
        ) { entry ->
            MovieDetailsScreen(
                rootNavHostController,
                mediaId = entry.arguments?.getInt(RootScreen.MovieDetails.ARGUMENT_ID)
            )
        }
        composable(
            route = RootScreen.TvDetails.route + "/{${RootScreen.TvDetails.ARGUMENT_ID}}",
            arguments = listOf(navArgument(RootScreen.TvDetails.ARGUMENT_ID) {
                type = NavType.IntType
            })
        ) { entry ->
            TvDetailsScreen(
                rootNavHostController,
                mediaId = entry.arguments?.getInt(RootScreen.TvDetails.ARGUMENT_ID)
            )
        }
    }
}
