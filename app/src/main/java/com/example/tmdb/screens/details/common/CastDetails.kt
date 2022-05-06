package com.example.tmdb.screens.details.common

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.room.util.TableInfo
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.tmdb.R
import com.example.tmdb.data.local.CastLocal
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
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            for (i in creditsResponse?.cast!!.take(3)) {
                CastInfo(castName = i.name, R.font.proximanova_bold)

            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            for (i in creditsResponse?.cast!!.take(3)) {
                CastInfo(castName = i.knownForDepartment, R.font.proximanova_regular)
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            for (i in creditsResponse?.cast!!.takeLast(3)) {
                CastInfo(castName = i.name, R.font.proximanova_bold)
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            for (i in creditsResponse?.cast!!.takeLast(3)) {
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
            .size(width = 140.dp, height = 220.dp)
            .padding(horizontal = 5.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(bounded = true, color = Color.Black),
                onClick = { //navController.navigate(BottomBarScreen.Details.route)
                }
            ),
        shape = RoundedCornerShape(6.dp),
        elevation = 15.dp,
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
                    .height(140.dp)
                    .clip(shape = RoundedCornerShape(6.dp)),
                contentScale = ContentScale.Crop,

                )
            Text(
                modifier = Modifier
                    .padding(top = 6.dp, start = 6.dp, end = 6.dp),
                text = castName,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.proximanova_xbold)),
                color = Color.Black
            )
            Text(
                modifier = Modifier
                    .padding(top = 6.dp, start = 6.dp, end = 6.dp),
                text = castKnownFor,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.proximanova_regular)),
                color = Color.Black
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
            .padding(top = 20.dp)
    ) {
        LazyRow(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(top = 5.dp, start = 16.dp, end = 16.dp)
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
fun TopBilledCastSectionItemOffline(
) {
    val favouritesViewModel: FavouritesViewModel = hiltViewModel()
    val castLocal = favouritesViewModel.casts.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 20.dp)
    ) {
        LazyRow(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(top = 5.dp, start = 16.dp, end = 16.dp)
        ) {
            items(
                items = castLocal.value
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
