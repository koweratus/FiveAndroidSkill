package com.example.tmdb

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tmdb.screens.FavouritesScreen
import com.example.tmdb.screens.HomeScreen
import com.example.tmdb.screens.home.HomeViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun NavigationGraph (
    navController: NavHostController
){
    NavHost(navController, startDestination = BottomBarScreen.Home.route) {
        composable(BottomBarScreen.Home.route) {
            HomeScreen()
        }
        composable(BottomBarScreen.Favourites.route) {
            FavouritesScreen(navController)
        }
    }
}