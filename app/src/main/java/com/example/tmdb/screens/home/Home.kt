package com.example.tmdb.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
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
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.tmdb.R
import com.example.tmdb.data.local.CastLocal
import com.example.tmdb.data.local.CrewLocal
import com.example.tmdb.data.local.Favourite
import com.example.tmdb.data.local.FavouritesWithCastCrossRef
import com.example.tmdb.navigation.RootScreen
import com.example.tmdb.remote.responses.CreditsResponse
import com.example.tmdb.remote.responses.TvDetails
import com.example.tmdb.screens.details.DetailsViewModel
import com.example.tmdb.screens.favourites.FavouritesViewModel
import com.example.tmdb.screens.home.HomeViewModel
import com.example.tmdb.screens.widgets.FavoriteButton
import com.example.tmdb.screens.widgets.SearchAppBar
import com.example.tmdb.screens.widgets.SectionText
import com.example.tmdb.screens.widgets.Tabs
import com.example.tmdb.utils.Constants.IMAGE_BASE_UR
import com.example.tmdb.utils.Resource
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.emptyFlow

@ExperimentalPagerApi
@Composable
fun HomeScreen(
    navController: NavController
) {
    TabScreen(navController)
}

@ExperimentalPagerApi
@Composable
fun TabScreen(navController: NavController) {

    val viewModel: HomeViewModel = hiltViewModel()
    val popularMovies = viewModel.popularMovies.collectAsLazyPagingItems()
    val trendingMoviesDay = viewModel.trendingMoviesDay.collectAsLazyPagingItems()
    val trendingMoviesWeek = viewModel.trendingMoviesWeek.collectAsLazyPagingItems()
    val upcomingMovies = viewModel.upcomingMovies.collectAsLazyPagingItems()
    val nowPlayingMovies = viewModel.nowPlayingMovies.collectAsLazyPagingItems()

    val onAirTv = viewModel.onAirTv.collectAsLazyPagingItems()
    val popularTv = viewModel.popularTv.collectAsLazyPagingItems()


    val pagerStateFirstTab = rememberPagerState(initialPage = 0)
    val pagerStateSecondTab = rememberPagerState(initialPage = 0)
    val pagerStateThirdTab = rememberPagerState(initialPage = 0)
    val listFirstTab = listOf(
        stringResource(R.string.streaming), stringResource(R.string.onTv), stringResource(
            R.string.inTheaters
        )
    )
    val listSecondTab = listOf(stringResource(R.string.movies), stringResource(R.string.tv))
    val listThirdTab = listOf(stringResource(R.string.today), stringResource(R.string.thisWeek))

    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly,
    )
    {
        item {
            Spacer(modifier = Modifier.padding(40.dp))

            SearchAppBar(
                text = "",
                onTextChange = {},
                onCloseClicked = { /*TODO*/ },
                onSearchClicked = {})
            Spacer(modifier = Modifier.padding(20.dp))

            SectionText(text = stringResource(R.string.whatsPopular))
            Tabs(pagerState = pagerStateFirstTab, listFirstTab)
            when (pagerStateFirstTab.targetPage) {
                0 -> TabsContent(
                    pagerState = pagerStateFirstTab,
                    listFirstTab.size,
                    popularMovies,
                    navController, RootScreen.MovieDetails.route, "movie"

                )
                1 -> TabsContent(
                    pagerState = pagerStateFirstTab,
                    listFirstTab.size,
                    popularTv,
                    navController, RootScreen.TvDetails.route, "tv"

                )
                2 -> TabsContent(
                    pagerState = pagerStateFirstTab,
                    listFirstTab.size,
                    upcomingMovies,
                    navController, RootScreen.MovieDetails.route, "movie"

                )
            }
            Spacer(modifier = Modifier.padding(20.dp))
        }
        item {
            SectionText(text = stringResource(R.string.freeToWatch))
            Tabs(pagerState = pagerStateSecondTab, listSecondTab)
            when (pagerStateSecondTab.targetPage) {
                0 -> TabsContent(
                    pagerState = pagerStateSecondTab,
                    listSecondTab.size,
                    nowPlayingMovies, navController, RootScreen.MovieDetails.route, "movie"

                )
                1 -> TabsContent(
                    pagerState = pagerStateSecondTab,
                    listSecondTab.size,
                    onAirTv, navController, RootScreen.TvDetails.route, "tv"

                )
            }

            Spacer(modifier = Modifier.padding(20.dp))
        }
        item {
            SectionText(text = stringResource(R.string.trending))
            Tabs(pagerState = pagerStateThirdTab, listThirdTab)
            when (pagerStateThirdTab.targetPage) {
                0 -> TabsContent(
                    pagerState = pagerStateThirdTab,
                    listThirdTab.size,
                    trendingMoviesDay,
                    navController,
                    RootScreen.MovieDetails.route, "movie"

                )
                1 -> TabsContent(
                    pagerState = pagerStateThirdTab,
                    listThirdTab.size,
                    trendingMoviesWeek,
                    navController,
                    RootScreen.MovieDetails.route, "movie"

                )
            }
            Spacer(modifier = Modifier.padding(40.dp))
        }

    }
}

@ExperimentalPagerApi
@Composable
fun <T : Any> TabsContent(
    pagerState: PagerState,
    count: Int,
    list: LazyPagingItems<T>,
    navController: NavController,
    route: String,
    type: String,
) {
    if (type == "movie") {
        System.out.println("AHAHAHAHAHAH")
    }
    HorizontalPager(
        count,
        state = pagerState,
        verticalAlignment = Alignment.Top
    ) { page ->
        when (page) {
            0 -> RowSectionItem(list, navController, route, type)
            1 -> RowSectionItem(list, navController, route, type)
            2 -> RowSectionItem(list, navController, route, type)
        }
    }
}

@ExperimentalPagerApi
@Composable
fun <T : Any> RowSectionItem(
    list: LazyPagingItems<T>,
    navController: NavController?,
    route: String,
    type: String,
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
                items = list
            ) { item ->
                navController?.let { RowItem(posterUrl = item, it, route, type) }
            }
        }
    }
}

@Composable
fun RowItem(
    posterUrl: Any?,
    navController: NavController,
    route: String,
    type: String
) {
    val context = LocalContext.current
    val viewModel: FavouritesViewModel = hiltViewModel()

    //get poster whether its movie or tv media
    val getMediaPosterUrl = posterUrl?.javaClass?.getDeclaredField("posterPath")
    getMediaPosterUrl?.isAccessible = true

    val getId = posterUrl?.javaClass?.getDeclaredField("id")
    getId?.isAccessible = true
    val getMediaTitle = posterUrl?.javaClass?.getDeclaredField("title")
    getMediaTitle?.isAccessible = true

    val getReleaseDate = posterUrl?.javaClass?.getDeclaredField("releaseDate")
    getReleaseDate?.isAccessible = true

    val getRating = posterUrl?.javaClass?.getDeclaredField("voteAverage")
    getRating?.isAccessible = true

    val getOverview = posterUrl?.javaClass?.getDeclaredField("overview")
    getOverview?.isAccessible = true

    val detailsViewModel: DetailsViewModel = hiltViewModel()

    val tvCasts = produceState<Resource<CreditsResponse>>(initialValue = Resource.Loading()) {
        value = detailsViewModel.getTvCasts(getId?.get(posterUrl) as Int)
    }.value
    val movieCasts = produceState<Resource<CreditsResponse>>(initialValue = Resource.Loading()) {
        value = detailsViewModel.getMovieCasts(getId?.get(posterUrl) as Int)
    }.value


    //val getRunTime = posterUrl?.javaClass?.getDeclaredField("runTime")
    // getRunTime?.isAccessible = true
    Card(
        modifier = Modifier
            .size(width = 140.dp, height = 220.dp)
            .padding(horizontal = 5.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(bounded = true, color = Color.Black),
                onClick = {
                    navController.navigate(route + "/${getId?.get(posterUrl)}")
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
                        .data("$IMAGE_BASE_UR/${getMediaPosterUrl?.get(posterUrl)}")
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
            FavoriteButton(isLiked = viewModel.isAFavorite(getId?.get(posterUrl) as Int)
                .collectAsState(false).value != null,
                onClick = { isFav ->
                    if (isFav) {
                        viewModel.deleteOneFavorite(getId?.get(posterUrl) as Int)
                        viewModel.deleteCast(getId?.get(posterUrl) as Int)
                        viewModel.deleteCrew(getId?.get(posterUrl) as Int)
                        viewModel.deleteFavouritesWithCastCrossRef(getId?.get(posterUrl) as Int)
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
                                mediaId = getId?.get(posterUrl) as Int,
                                mediaType = type,
                                image = "$IMAGE_BASE_UR/${getMediaPosterUrl?.get(posterUrl)}",
                                title = getMediaTitle?.get(posterUrl) as String,
                                releaseDate = getReleaseDate?.get(posterUrl) as String,
                                rating = getRating?.get(posterUrl) as Float,
                                runTime = "",
                                genres = "",
                                overview = getOverview?.get(posterUrl) as String
                            )
                        )
                        if (type == "movie") {
                            movieCasts?.data?.cast?.forEach {
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
                                        movieIdForCast = getId?.get(posterUrl) as Int
                                    )
                                )
                                viewModel.insertFavouritesWithCast(
                                    FavouritesWithCastCrossRef(
                                        id = it.id,
                                        mediaId = getId?.get(posterUrl) as Int
                                    )
                                )
                            }
                            movieCasts.data?.crew?.forEach {
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
                                        movieIdForCrew =  getId?.get(posterUrl) as Int,
                                        department = it.department,
                                        job = it.job
                                    )
                                )
                            }

                        } else {
                            tvCasts?.data?.cast?.forEach {
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
                                        movieIdForCast = getId?.get(posterUrl) as Int
                                    )
                                )
                                viewModel.insertFavouritesWithCast(
                                    FavouritesWithCastCrossRef(
                                        id = it.id,
                                        mediaId = getId?.get(posterUrl) as Int
                                    )
                                )
                            }
                            tvCasts.data?.crew?.forEach {
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
                                        movieIdForCrew =  getId?.get(posterUrl) as Int,
                                        department = it.department,
                                        job = it.job
                                    )
                                )
                            }

                        }

                    }
                })
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




