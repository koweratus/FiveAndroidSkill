package com.example.tmdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tmdb.ui.theme.TmdbTheme
import com.google.accompanist.pager.ExperimentalPagerApi

class MainActivity : ComponentActivity() {
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TmdbTheme {
                MainScren()
                ScaffoldWithTopBar()
            }
        }
    }
}

@Composable
fun ScaffoldWithTopBar() {
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentSize()
    ) {

        TopAppBar(
            title = {
                Text(text = "TopAppBar")
            },

            backgroundColor = colorResource(R.color.purple_700),
            contentColor = colorResource(R.color.purple_700),
        )
        Image(
            painterResource(R.drawable.tmdblogo),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .height(50.dp)
                .fillMaxWidth(.6f)
        )

    }


}