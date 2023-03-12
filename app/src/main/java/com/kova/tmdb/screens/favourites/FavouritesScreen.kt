package com.example.tmdb.screens.favourites

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.tmdb.R
import com.example.tmdb.data.local.Favourite
import com.example.tmdb.navigation.RootScreen
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

    Column(
        Modifier.padding(
            top = dimensionResource(id = R.dimen.padding_50),
            bottom = dimensionResource(id = R.dimen.padding_40)
        )
    ) {
        SectionText(stringResource(R.string.favs))
        LazyVerticalGrid(
            columns = GridCells.Adaptive(dimensionResource(id = R.dimen.grid_cells)),
            contentPadding = PaddingValues(
                start = dimensionResource(id = R.dimen.padding_12),
                top = dimensionResource(id = R.dimen.padding_66),
                end = dimensionResource(id = R.dimen.padding_12),
                bottom = dimensionResource(id = R.dimen.small_padding)
            ),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.SpaceEvenly,
            content = {
                items(items = favoriteFilms.value, key = { favoriteFilm: Favourite ->
                    favoriteFilm.mediaId
                }) { favourite ->
                    GridItem(moviesData = favourite, navController, viewModel)
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
            .height(dimensionResource(id = R.dimen.height_180))
            .padding(dimensionResource(id = R.dimen.padding_4))
            .clickable {
                if (moviesData.mediaType == "tv") {
                    navController.navigate("${RootScreen.TvDetails.route}/${moviesData.mediaId}")
                } else if (moviesData.mediaType == "movie") {
                    navController.navigate("${RootScreen.MovieDetails.route}/${moviesData.mediaId}")
                }

            },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shaped_corners_big)),
        elevation = dimensionResource(id = R.dimen.elevation_12)
    ) {
        Box(modifier = Modifier.height(dimensionResource(id = R.dimen.height_medium))) {
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
                    .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.shaped_corners)))
            )
            FavoriteButton(
                isLiked = viewModel.isAFavorite(moviesData.mediaId)
                    .collectAsState(false).value != null,
                onClick = { isFav ->
                    if (isFav) {
                        viewModel.deleteOneFavorite(moviesData.mediaId)
                        viewModel.deleteCast(moviesData.mediaId)
                        viewModel.deleteCrew(moviesData.mediaId)
                        viewModel.deleteFavouritesWithCastCrossRef(moviesData.mediaId)
                        Toast.makeText(
                            context,
                            "Successfully deleted a favourite.",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@FavoriteButton
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
