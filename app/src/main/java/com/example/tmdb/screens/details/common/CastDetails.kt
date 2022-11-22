package com.example.tmdb.screens.details.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.tmdb.R
import com.example.tmdb.data.local.Favourite
import com.example.tmdb.data.local.FavouritesWithCast
import com.example.tmdb.data.local.FavouritesWithCrew
import com.example.tmdb.remote.responses.CreditsResponse
import com.example.tmdb.screens.favourites.FavouritesViewModel
import com.example.tmdb.utils.Constants
import com.google.accompanist.pager.ExperimentalPagerApi

@Composable
fun CastDetails(creditsResponse: CreditsResponse?) {

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.small_padding),
                    end = dimensionResource(id = R.dimen.small_padding),
                    top = dimensionResource(id = R.dimen.small_padding)
                )
                .align(Alignment.CenterHorizontally)
        ) {
            for (i in creditsResponse?.crew!!.take(3)) {
                CastInfo(castName = i.name, R.font.proximanova_bold)

            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.small_padding),
                    end = dimensionResource(id = R.dimen.small_padding),
                    top = dimensionResource(id = R.dimen.small_padding)
                )
                .align(Alignment.CenterHorizontally)
        ) {
            for (i in creditsResponse?.crew!!.take(3)) {
                CastInfo(castName = i.knownForDepartment, R.font.proximanova_regular)
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.small_padding),
                    end = dimensionResource(id = R.dimen.small_padding),
                    top = dimensionResource(id = R.dimen.small_padding)
                )
                .align(Alignment.CenterHorizontally)
        ) {
            for (i in creditsResponse?.crew!!.takeLast(3)) {
                CastInfo(castName = i.name, R.font.proximanova_bold)
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.small_padding),
                    end = dimensionResource(id = R.dimen.small_padding),
                    top = dimensionResource(id = R.dimen.small_padding)
                )
                .align(Alignment.CenterHorizontally)
        ) {
            for (i in creditsResponse?.crew!!.takeLast(3)) {
                CastInfo(castName = i.knownForDepartment, R.font.proximanova_regular)
            }
        }
    }
}

@Composable
fun CastInfo(castName: String, font: Int) {
    Text(
        text = castName,
        color = Color.Black,
        fontFamily = FontFamily(Font(font)),
        fontSize = 14.sp,
    )
}

@Composable
fun TopBilledCastItem(
    castName: String,
    castPhoto: String,
    castKnownFor: String,
) {

    Card(
        modifier = Modifier
            .size(
                width = dimensionResource(id = R.dimen.width_140),
                height = dimensionResource(id = R.dimen.height_220)
            )
            .padding(horizontal = dimensionResource(id = R.dimen.xsmall_padding))
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(bounded = true, color = Color.Black),
                onClick = { //navController.navigate(BottomBarScreen.Details.route)
                }
            ),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shaped_corners)),
        elevation = dimensionResource(id = R.dimen.shaped_corners_big),
        backgroundColor = Color.White
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .crossfade(true)
                        .data(castPhoto)
                        .build(),
                    filterQuality = FilterQuality.High,
                    contentScale = ContentScale.Crop

                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.height_140))
                    .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.shaped_corners))),
                contentScale = ContentScale.Crop,

                )
            Text(
                modifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.xsmall_padding),
                        start = dimensionResource(id = R.dimen.xsmall_padding),
                        end = dimensionResource(id = R.dimen.xsmall_padding)
                    ),
                text = castName,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.proximanova_xbold)),
                color = Color.Black
            )
            Text(
                modifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.xsmall_padding),
                        start = dimensionResource(id = R.dimen.xsmall_padding),
                        end = dimensionResource(id = R.dimen.xsmall_padding)
                    ),
                text = castKnownFor,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.proximanova_regular)),
                color = Color.Black
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.White
                            ),
                            startY = 300f
                        )
                    )
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TopBilledCastSectionItem(
    list: CreditsResponse?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = dimensionResource(id = R.dimen.medium_padding))
    ) {
        LazyRow(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.xsmall_padding))
        ) {
            items(
                list?.cast!!
            ) { item ->
                TopBilledCastItem(
                    castName = item.name,
                    castKnownFor = item.knownForDepartment,
                    castPhoto = "${Constants.IMAGE_BASE_UR}/${item.profilePath}"
                )
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TopBilledCastSectionItemOffline(mediaId: Int) {
    val favouritesViewModel: FavouritesViewModel = hiltViewModel()
    val castLocal = favouritesViewModel.getFavouritesWithCast(mediaId).collectAsState(
        initial = FavouritesWithCast(
            castLocal = emptyList(),
            favourite = Favourite(
                mediaId = mediaId,
                mediaType = "",
                image = "",
                rating = 0f,
                favourite = false,
                releaseDate = "",
                title = "",
                runTime = "",
                genres = "",
                overview = ""
            )
        )
    ).value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = dimensionResource(id = R.dimen.medium_padding))
    ) {
        LazyRow(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.xsmall_padding))
        ) {
            items(
                items = castLocal.castLocal
            ) { item ->
                TopBilledCastItem(
                    castName = item.name,
                    castKnownFor = item.knownForDepartment,
                    castPhoto = "${Constants.IMAGE_BASE_UR}/${item.profilePath}"
                )
            }
        }
    }
}

@Composable
fun CastDetailsOffline(mediaId: Int) {

    val favouritesViewModel: FavouritesViewModel = hiltViewModel()
    val crewLocal = favouritesViewModel.getFavouritesWithCrew(mediaId).collectAsState(
        initial = FavouritesWithCrew(
            crewLocal = emptyList(),
            favourite = Favourite(
                mediaId = mediaId,
                mediaType = "",
                image = "",
                rating = 0f,
                favourite = false,
                releaseDate = "",
                title = "",
                runTime = "",
                genres = "",
                overview = ""
            )
        )
    ).value

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.small_padding),
                    end = dimensionResource(id = R.dimen.small_padding),
                    top = dimensionResource(id = R.dimen.small_padding)
                )
                .align(Alignment.CenterHorizontally)
        ) {
            for (i in crewLocal.crewLocal.take(3)) {
                CastInfo(castName = i.name, R.font.proximanova_bold)
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.xsmall_padding),
                    end = dimensionResource(id = R.dimen.small_padding),
                    top = dimensionResource(id = R.dimen.small_padding)
                )
                .align(Alignment.CenterHorizontally)
        ) {
            for (i in crewLocal.crewLocal.take(3)) {
                CastInfo(castName = i.knownForDepartment, R.font.proximanova_regular)
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.small_padding),
                    end = dimensionResource(id = R.dimen.small_padding),
                    top = dimensionResource(id = R.dimen.small_padding)
                )
                .align(Alignment.CenterHorizontally)
        ) {
            for (i in crewLocal.crewLocal.takeLast(3)) {
                CastInfo(castName = i.name, R.font.proximanova_bold)
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.small_padding),
                    end = dimensionResource(id = R.dimen.small_padding),
                    top = dimensionResource(id = R.dimen.small_padding)
                )
                .align(Alignment.CenterHorizontally)
        ) {
            for (i in crewLocal.crewLocal.takeLast(3)) {
                CastInfo(castName = i.knownForDepartment, R.font.proximanova_regular)
            }
        }
    }
}
