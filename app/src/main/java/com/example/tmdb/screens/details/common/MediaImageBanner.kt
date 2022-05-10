package com.example.tmdb.screens.details.common

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.tmdb.R
import com.example.tmdb.data.local.CastLocal
import com.example.tmdb.data.local.CrewLocal
import com.example.tmdb.data.local.Favourite
import com.example.tmdb.data.local.FavouritesWithCastCrossRef
import com.example.tmdb.remote.responses.CreditsResponse
import com.example.tmdb.screens.favourites.FavouritesViewModel
import com.example.tmdb.screens.widgets.FavoriteButton
import com.example.tmdb.utils.Resource

@Composable
fun ImageItem(
    mediaPosterUrl: String,
    mediaName: String,
    rating: Float?,
    mediaReleaseDate: String,
    genres: String,
    runTime: String,
    viewModel: FavouritesViewModel,
    mediaType: String,
    overview: String,
    mediaId: Int,
    casts: Resource<CreditsResponse>
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxSize(1f)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(bounded = true, color = Color.Black),
                onClick = { }
            )
            .height(300.dp),
        elevation = 5.dp
    ) {
        Box(modifier = Modifier.height(10.dp)) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .crossfade(true)
                        .data(mediaPosterUrl)
                        .build(),
                    filterQuality = FilterQuality.High,
                    contentScale = ContentScale.Crop

                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .fillMaxWidth(1f)
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
            Row(modifier = Modifier.padding(start = 16.dp, top = 105.dp)) {
                rating?.let { CircularProgressionBar(percentage = it) }
                Text(
                    text = stringResource(R.string.user_score),
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.proximanova_bold)),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 16.dp)
                )
            }
            Text(
                text = mediaName,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.proximanova_xbold)),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 160.dp, start = 10.dp)
            )
            Text(
                text = mediaReleaseDate,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.proximanova_regular)),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 190.dp, start = 10.dp)
            )
            Text(
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.proximanova_regular)),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 208.dp, start = 10.dp),
                text = genres + stringResource(R.string.runtime, runTime)
            )
            /*Icon(
                imageVector =
                Icons.Default.FavoriteBorder,
                contentDescription = null,
                modifier = Modifier.padding(top = 250.dp, start = 10.dp),
                tint = Color.White
            )*/
            FavoriteButton(
                isLiked = viewModel.isAFavorite(mediaId).collectAsState(false).value != null,
                onClick = { isFav ->
                    if (isFav) {
                        viewModel.deleteOneFavorite(mediaId)
                        viewModel.deleteCast(mediaId)
                        viewModel.deleteCrew(mediaId)
                        viewModel.deleteFavouritesWithCastCrossRef(mediaId)
                        Toast.makeText(
                            context,
                            "Successfully deleted a favourite.",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@FavoriteButton
                    } else {
                        viewModel.insertFavorite(
                            Favourite(
                                favourite = true,
                                mediaId = mediaId,
                                mediaType = mediaType,
                                image = mediaPosterUrl,
                                title = mediaName,
                                releaseDate = mediaReleaseDate,
                                rating = rating!!,
                                genres = genres,
                                runTime = runTime,
                                overview = overview
                            )
                        )
                        casts.data?.cast?.forEach {
                            viewModel.insertCast(
                                CastLocal(
                                    castId = it.castId,
                                    adult = false,
                                    character = it.character,
                                    creditId = it.creditId,
                                    gender = it.gender,
                                    id = it.id,
                                    knownForDepartment = it.knownForDepartment,
                                    name = it.name,
                                    order = it.order,
                                    originalName = it.originalName,
                                    popularity = it.popularity,
                                    profilePath = it.profilePath,
                                    movieIdForCast = mediaId
                                )
                            )
                            viewModel.insertFavouritesWithCast(
                                FavouritesWithCastCrossRef(
                                    id = it.id,
                                    mediaId = mediaId
                                )
                            )
                        }
                        casts.data?.crew?.forEach {
                            viewModel.insertCrew(
                                CrewLocal(
                                    adult = false,
                                    creditId = it.creditId,
                                    gender = it.gender,
                                    id = it.id,
                                    knownForDepartment = it.knownForDepartment,
                                    name = it.name,
                                    originalName = it.originalName,
                                    popularity = it.popularity,
                                    profilePath = it.profilePath,
                                    movieIdForCrew = mediaId,
                                    department = it.department,
                                    job = it.job
                                )
                            )
                        }

                    }
                }
            )
        }

    }

}