package com.example.tmdb

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int,
){
    object Home: BottomBarScreen(
        route = "home",
        title = "Home",
        icon= R.drawable.ic_home
    )
    object Favourites: BottomBarScreen(
        route = "favourites",
        title = "Favourites",
        icon= R.drawable.ic_favorite
    )
}
