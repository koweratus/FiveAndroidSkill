package com.example.tmdb.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.tmdb.R
import com.example.tmdb.remote.responses.CreditsResponse
import com.example.tmdb.remote.responses.ReviewResponse
import com.example.tmdb.remote.responses.TvDetails
import com.example.tmdb.remote.responses.TvResponse
import com.example.tmdb.screens.details.DetailsViewModel
import com.example.tmdb.screens.details.common.ImageItem
import com.example.tmdb.screens.details.common.Overview
import com.example.tmdb.screens.details.common.TabsContentForSocial
import com.example.tmdb.screens.details.common.TopBilledCastSectionItem
import com.example.tmdb.screens.widgets.SectionText
import com.example.tmdb.screens.widgets.Tabs
import com.example.tmdb.utils.Constants
import com.example.tmdb.utils.Resource
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@ExperimentalPagerApi
@Composable
fun TvDetailsScreen(navController: NavController, mediaId: Int?) {
    val viewModel: DetailsViewModel = hiltViewModel()
    val details = produceState<Resource<TvDetails>>(initialValue = Resource.Loading()) {
        value = viewModel.getTvDetails(mediaId)
    }.value
    val casts = produceState<Resource<CreditsResponse>>(initialValue = Resource.Loading()) {
        value = viewModel.getTvCasts(mediaId!!)
    }.value
    val review = produceState<Resource<ReviewResponse>>(initialValue = Resource.Loading()) {
        value = viewModel.getTvReviews(mediaId!!)
    }.value

    val recommendation = produceState<Resource<TvResponse>>(initialValue = Resource.Loading()) {
        value = viewModel.getTvRecommendations(mediaId!!)
    }.value
    val pagerState = rememberPagerState(initialPage = 0)
    val listFirstTab = listOf(
        stringResource(R.string.reviews), stringResource(R.string.discussions)
    )

    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        item {
            ImageItem(
                mediaPosterUrl = "${Constants.IMAGE_BASE_UR}/${details.data?.posterPath}",
                mediaName = details.data?.name.toString(),
                mediaReleaseDate = details.data?.firstAirDate.toString(),
                rating = details.data?.voteAverage?.toFloat(),
                genres = details.data?.genres?.joinToString {
                    it.name
                }.toString(),
                runTime = details.data?.episodeRunTime.toString()

            )
            Overview(navController, casts = casts, overview = details.data?.overview.toString())
            SectionText("Top Billed Cast")
            if (casts is Resource.Success) {
                TopBilledCastSectionItem(list = casts.data!!)
            }
            Spacer(Modifier.padding(35.dp))
            SectionText(stringResource(R.string.social))
            Tabs(pagerState = pagerState, listFirstTab)
            if (review is Resource.Success) {
                TabsContentForSocial(pagerState = pagerState, listFirstTab.size, review.data!!)
            }
            SectionText(stringResource(R.string.recommendations))
            if (recommendation is Resource.Success) {
                RowRecommendationsItem(recommendation.data!!, navController)
            }
            Spacer(Modifier.padding(35.dp))
        }
    }
}

@Composable
private fun RowRecommendationsItem(
    list: TvResponse,
    navController: NavController
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
                .padding(top = 5.dp, start = 16.dp)
        ) {
            items(
                items = list.results
            ) { item ->
                RowItemRecommendations(
                    movieName = item.name,
                    moviePoster = item.posterPath,
                    id = item.id,
                    navController = navController
                )
            }
        }
    }
}

@Composable
private fun RowItemRecommendations(
    moviePoster: String,
    movieName: String,
    id: Int,
    navController: NavController
) {

    Column(
    ) {
        Card(
            modifier = Modifier
                .size(width = 220.dp, height = 100.dp)
                .padding(horizontal = 5.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(bounded = true, color = Color.Black),
                    onClick = {
                        navController.navigate("tv_details_screen/${id}")
                    }
                ),
            shape = RoundedCornerShape(15.dp),
            elevation = 5.dp
        ) {
            Box(modifier = Modifier.height(200.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .crossfade(true)
                            .data("${Constants.IMAGE_BASE_UR}/${moviePoster}")
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
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black
                                ),
                                startX = 300f
                            )
                        )
                )
            }
        }

        Text(
            text = movieName,
            color = colorResource(R.color.purple_700),
            textAlign = TextAlign.Start,
            fontFamily = FontFamily(Font(R.font.proximanova_bold)),
            fontSize = 16.sp,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
        )
    }
}

