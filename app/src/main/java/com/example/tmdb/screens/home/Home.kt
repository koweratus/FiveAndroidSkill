package com.example.tmdb.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.tmdb.BottomBarScreen
import com.example.tmdb.R
import com.example.tmdb.screens.home.HomeViewModel
import com.example.tmdb.screens.widgets.FavoriteButton
import com.example.tmdb.screens.widgets.SearchAppBar
import com.example.tmdb.screens.widgets.SectionText
import com.example.tmdb.screens.widgets.Tabs
import com.example.tmdb.utils.Constants.IMAGE_BASE_UR
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

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
    val popularMovies = viewModel.popularMovies.value.collectAsLazyPagingItems()
    val trendingMoviesDay = viewModel.trendingMoviesDay.value.collectAsLazyPagingItems()
    val trendingMoviesWeek = viewModel.trendingMoviesWeek.value.collectAsLazyPagingItems()
    val upcomingMovies = viewModel.upcomingMovies.value.collectAsLazyPagingItems()
    val topRatedMovies = viewModel.topRatedMovies.value.collectAsLazyPagingItems()
    val nowPlayingMovies = viewModel.nowPlayingMovies.value.collectAsLazyPagingItems()

    val trendingTv = viewModel.trendingTv.value.collectAsLazyPagingItems()
    val onAirTv = viewModel.onAirTv.value.collectAsLazyPagingItems()
    val airingTodayTv = viewModel.airingTodayTv.value.collectAsLazyPagingItems()
    val popularTv = viewModel.popularTv.value.collectAsLazyPagingItems()

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
                    navController, "movie_details_screen"
                )
                1 -> TabsContent(
                    pagerState = pagerStateFirstTab,
                    listFirstTab.size,
                    popularTv,
                    navController, "tv_details_screen"
                )
                2 -> TabsContent(
                    pagerState = pagerStateFirstTab,
                    listFirstTab.size,
                    upcomingMovies,
                    navController, "movie_details_screen"
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
                    nowPlayingMovies, navController, "movie_details_screen"
                )
                1 -> TabsContent(
                    pagerState = pagerStateSecondTab,
                    listSecondTab.size,
                    onAirTv, navController, "tv_details_screen"
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
                    "movie_details_screen"
                )
                1 -> TabsContent(
                    pagerState = pagerStateThirdTab,
                    listThirdTab.size,
                    trendingMoviesWeek,
                    navController,
                    "movie_details_screen"
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
    route: String
) {
    HorizontalPager(
        count,
        state = pagerState,
        verticalAlignment = Alignment.Top
    ) { page ->
        when (page) {
            0 -> RowSectionItem(list, navController, route)
            1 -> RowSectionItem(list, navController, route)
            2 -> RowSectionItem(list, navController, route)
        }
    }
}

@ExperimentalPagerApi
@Composable
fun <T : Any> RowSectionItem(
    list: LazyPagingItems<T>,
    navController: NavController?,
    route: String
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
                navController?.let { RowItem(posterUrl = item, it, route) }
            }
        }
    }
}

@Composable
fun RowItem(
    posterUrl: Any?,
    navController: NavController,
    route: String
) {

    //get poster whether its movie or tv media
    val getMediaPosterUrl = posterUrl?.javaClass?.getDeclaredField("posterPath")
    getMediaPosterUrl?.isAccessible = true

    val getId = posterUrl?.javaClass?.getDeclaredField("id")
    getId?.isAccessible = true
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
            FavoriteButton(modifier = Modifier.padding(12.dp))
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




