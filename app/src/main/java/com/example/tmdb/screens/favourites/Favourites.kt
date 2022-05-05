package com.example.tmdb.screens

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.tmdb.R
import com.example.tmdb.data.local.Favourite
import com.example.tmdb.navigation.RootScreen
import com.example.tmdb.screens.favourites.FavouritesViewModel
import com.example.tmdb.screens.widgets.FavoriteButton
import com.example.tmdb.screens.widgets.SectionText
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun FavouritesScreen(navController: NavController) {

    //
    val viewModel: FavouritesViewModel = hiltViewModel()
    val favoriteFilms = viewModel.favourites.collectAsState(initial = emptyList())

    Column(Modifier.padding(top = 50.dp, bottom = 40.dp)) {
        SectionText(stringResource(R.string.favs))
        LazyVerticalGrid(
            cells = GridCells.Adaptive(105.dp),
            // content padding
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 66.dp,
                end = 12.dp,
                bottom = 16.dp
            ),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.SpaceEvenly,
            content = {
                items(items = favoriteFilms.value,  key = { favoriteFilm: Favourite ->
                    favoriteFilm.mediaId
                }){
                    favourite ->
                    GridItem(moviesData = favourite, navController ,viewModel)
                }
            }
        )
    }
}


@Composable
fun GridItem(
    moviesData: Favourite,
    navController: NavController,
    viewModel: FavouritesViewModel
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(4.dp)
            .clickable {
                if (moviesData.mediaType == "tv") {
                    navController.navigate(RootScreen.TvDetails.route + "/${moviesData.mediaId}")
                } else if (moviesData.mediaType == "movie") {
                    navController.navigate(RootScreen.MovieDetails.route + "/${moviesData.mediaId}")
                }

            },
        shape = RoundedCornerShape(15.dp),
        elevation = 12.dp
    ) {
        Box(modifier = Modifier.height(200.dp)) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .crossfade(true)
                        .data(moviesData.image)
                        .build(),
                    filterQuality = FilterQuality.High,
                    contentScale = ContentScale.Crop

                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(shape = RoundedCornerShape(6.dp))
            )
           // FavoriteButton(modifier = Modifier.padding(12.dp))
            FavoriteButton(
                isLiked = viewModel.isAFavorite(moviesData.mediaId).collectAsState(false).value != null,
                onClick = { isFav ->
                    if (isFav) {
                        viewModel.deleteOneFavorite(moviesData.mediaId)
                        Toast.makeText(context, "Successfully deleted a favourite.", Toast.LENGTH_SHORT).show()
                        return@FavoriteButton
                    } else {
                        viewModel.insertFavorite(
                            Favourite(
                                favourite = true,
                                mediaId = moviesData.mediaId,
                                mediaType = moviesData.mediaType,
                                image = moviesData.image,
                                title = moviesData.title,
                                releaseDate = moviesData.releaseDate,
                                rating = moviesData.rating
                            )
                        )
                    }
                }
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 300f
                        )
                    )
            )
        }
    }


}
