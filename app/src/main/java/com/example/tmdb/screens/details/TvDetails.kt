package com.example.tmdb.screens.details

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
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.tmdb.R
import com.example.tmdb.TopBar
import com.example.tmdb.data.local.Favourite
import com.example.tmdb.data.local.FavouritesWithCast
import com.example.tmdb.navigation.RootScreen
import com.example.tmdb.remote.responses.CreditsResponse
import com.example.tmdb.remote.responses.ReviewResponse
import com.example.tmdb.remote.responses.TvDetails
import com.example.tmdb.remote.responses.TvResponse
import com.example.tmdb.screens.details.common.*
import com.example.tmdb.screens.favourites.FavouritesViewModel
import com.example.tmdb.screens.widgets.SectionText
import com.example.tmdb.screens.widgets.Tabs
import com.example.tmdb.utils.Constants
import com.example.tmdb.utils.Resource
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@ExperimentalPagerApi
@Composable
fun TvDetailsScreen(
    navController: NavController,
    mediaId: Int?,
    favouritesViewModel: FavouritesViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = { TopBar(navController = navController) }
    ) {
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
        val favoriteFilms = favouritesViewModel.getFavouritesWithCast(mediaId!!).collectAsState(
            initial = FavouritesWithCast(
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
                ),
                castLocal = emptyList()
            )
        ).value

        LazyColumn(
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {

            item {

                if (details is Resource.Error) {
                    ImageItem(
                        mediaPosterUrl = favoriteFilms!!.favourite.image,
                        mediaName = favoriteFilms.favourite.title,
                        mediaReleaseDate = favoriteFilms.favourite.releaseDate,
                        rating = favoriteFilms.favourite.rating,
                        genres = favoriteFilms.favourite.genres.replace(
                            oldChar = ',',
                            newChar = ' '
                        ),
                        runTime = favoriteFilms.favourite.runTime,
                        viewModel = favouritesViewModel,
                        mediaId = favoriteFilms.favourite.mediaId,
                        mediaType = "tv",
                        overview = favoriteFilms.favourite.overview,
                        casts = casts
                    )
                    OverviewOffline(
                        overview = favoriteFilms.favourite.overview,
                        mediaId = favoriteFilms.favourite.mediaId
                    )
                    TopBilledCastSectionItemOffline(mediaId = favoriteFilms.favourite.mediaId)
                } else {
                    ImageItem(
                        mediaPosterUrl = "${Constants.IMAGE_BASE_UR}/${details.data?.posterPath}",
                        mediaName = details.data?.name.toString(),
                        mediaReleaseDate = details.data?.firstAirDate.toString(),
                        rating = details.data?.voteAverage?.toFloat(),
                        genres = details.data?.genres?.joinToString {
                            it.name
                        }.toString(),
                        runTime = details.data?.episodeRunTime.toString(),
                        viewModel = favouritesViewModel,
                        mediaId = details.data?.id ?: mediaId,
                        mediaType = "tv",
                        overview = details.data?.overview.toString(),
                        casts = casts
                    )
                    Overview(
                        navController,
                        casts = casts,
                        overview = details.data?.overview.toString(),
                        mediaId = mediaId
                    )
                    SectionText(stringResource(R.string.topBilledCast))
                    if (casts is Resource.Success) {
                        TopBilledCastSectionItem(list = casts.data!!)
                    }
                    Spacer(Modifier.padding(dimensionResource(id = R.dimen.spacer_value)))
                    SectionText(stringResource(R.string.social))
                    if (review is Resource.Success) {
                        Tabs(pagerState = pagerState, listFirstTab)
                        TabsContentForSocial(
                            pagerState = pagerState,
                            listFirstTab.size,
                            review.data!!
                        )
                    } else if (review is Resource.Error) {
                        SocialError()
                    }
                    SectionText(stringResource(R.string.recommendations))
                    if (recommendation is Resource.Success) {
                        RowRecommendationsItem(recommendation.data!!, navController)
                    }
                    Spacer(Modifier.padding(dimensionResource(id = R.dimen.spacer_value)))
                }
            }
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
            .padding(top = dimensionResource(id = R.dimen.medium_padding))
    ) {
        LazyRow(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = R.dimen.xsmall_padding)
                )
        ) {
            items(
                items = list.results
            ) { item ->
                RowItemRecommendations(
                    movieName = item.title,
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
    movieName: String?,
    id: Int,
    navController: NavController
) {

    Column(
    ) {
        Card(
            modifier = Modifier
                .size(
                    width = dimensionResource(id = R.dimen.width_220),
                    height = dimensionResource(id = R.dimen.height_medium)
                )
                .padding(horizontal = dimensionResource(id = R.dimen.xsmall_padding))
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(bounded = true, color = Color.Black),
                    onClick = {
                        navController.navigate("${RootScreen.TvDetails.route}/$id")
                    }
                ),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.shaped_corners_big)),
            elevation = dimensionResource(id = R.dimen.xsmall_padding)
        ) {
            Box(modifier = Modifier.height(dimensionResource(id = R.dimen.height_l))) {
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
                        .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.shaped_corners)))
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

        movieName?.let {
            Text(
                text = it,
                color = colorResource(R.color.purple_700),
                textAlign = TextAlign.Start,
                fontFamily = FontFamily(Font(R.font.proximanova_bold)),
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.small_padding),
                        top = dimensionResource(id = R.dimen.small_padding),
                        bottom = dimensionResource(id = R.dimen.small_padding)
                    )
            )
        }
    }
}
