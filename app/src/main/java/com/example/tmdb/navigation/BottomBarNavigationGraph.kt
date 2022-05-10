package com.example.tmdb

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tmdb.navigation.BottomBarScreen
import com.example.tmdb.screens.favourites.FavouritesScreen
import com.example.tmdb.screens.home.HomeScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun BottomBarNavigationGraph(
    rootNavHostController: NavHostController,
    bottomBarNavHostController: NavHostController
) {
    NavHost(bottomBarNavHostController, startDestination = BottomBarScreen.Home.route) {
        composable(BottomBarScreen.Home.route) {
            HomeScreen(rootNavHostController)
        }
        composable(BottomBarScreen.Favourites.route) {
            FavouritesScreen(rootNavHostController)
        }

    }
}