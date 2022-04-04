package com.example.tmdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tmdb.ui.theme.TmdbTheme
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TmdbTheme {
                MainScren()
                ScaffoldWithTopBar(navController = rememberNavController())
            }
        }
    }
}

@Composable
fun ScaffoldWithTopBar(navController: NavController) {
    var canPop by remember { mutableStateOf(false) }
    navController.addOnDestinationChangedListener { controller, _, _ ->
        canPop = controller.previousBackStackEntry != null
    }
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentSize()
    ) {

        TopAppBar(
            title = {
                Text(text = "TopAppBar")
            },
            navigationIcon = if (canPop) {
                {
                    IconButton(
                        onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White

                        )
                    }
                }
            } else {
                null
            },

            backgroundColor = colorResource(R.color.purple_700),
            contentColor = colorResource(R.color.purple_700),
        )
        Image(
            painterResource(R.drawable.tmdblogo),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .height(50.dp)
                .fillMaxWidth(.6f)
        )

    }


}
