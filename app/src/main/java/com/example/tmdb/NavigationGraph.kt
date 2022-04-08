package com.example.tmdb

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tmdb.screens.*
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun NavigationGraph (
    navController: NavHostController
){
    NavHost(navController, startDestination = BottomBarScreen.Home.route) {
        composable(BottomBarScreen.Home.route) {
            HomeScreen(navController)
        }
        composable(BottomBarScreen.Favourites.route) {
            FavouritesScreen(navController)
        }
        composable(route ="movie_details_screen"+"/{id}", arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
            })) {
            entry ->
            MovieDetailsScreen(navController, mediaId = entry.arguments?.getInt("id"))

        }
        composable(route ="tv_details_screen"+"/{id}", arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
            })) {
                entry ->
            TvDetailsScreen(navController, mediaId = entry.arguments?.getInt("id"))

        }
    }
}