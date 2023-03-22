package com.example.tmdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.navigation.compose.rememberNavController
import com.example.tmdb.ui.theme.TmdbTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(androidx.compose.animation.ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this) {}
        setContent {
            TmdbTheme {
                val navController = rememberNavController()
                RootNavigationGraph(rootNavHostController = navController)

            }
        }
    }
}
