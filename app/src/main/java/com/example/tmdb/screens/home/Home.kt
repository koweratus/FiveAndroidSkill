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
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.tmdb.R
import com.example.tmdb.screens.home.HomeViewModel
import com.example.tmdb.utils.Constants.IMAGE_BASE_UR
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun HomeScreen(
) {

    TabScreen(navController = rememberNavController())
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
                    navController
                )
                1 -> TabsContent(
                    pagerState = pagerStateFirstTab,
                    listFirstTab.size,
                    popularTv,
                    navController
                )
                2 -> TabsContent(
                    pagerState = pagerStateFirstTab,
                    listFirstTab.size,
                    upcomingMovies,
                    navController
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
                    nowPlayingMovies, navController
                )
                1 -> TabsContent(
                    pagerState = pagerStateSecondTab,
                    listSecondTab.size,
                    onAirTv, navController
                )
            }

            Spacer(modifier = Modifier.padding(20.dp))
        }
        item {
            stringResource(R.string.trending)
            Tabs(pagerState = pagerStateThirdTab, listThirdTab)
            when(pagerStateThirdTab.targetPage){
                0->  TabsContent(
                    pagerState = pagerStateThirdTab,
                    listThirdTab.size,
                    trendingMoviesDay,
                    navController
                )
                1->  TabsContent(
                    pagerState = pagerStateThirdTab,
                    listThirdTab.size,
                    trendingMoviesWeek,
                    navController
                )
            }
            Spacer(modifier = Modifier.padding(40.dp))
        }

    }
}

@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState, list: List<String>) {

    val scope = rememberCoroutineScope()
    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.Transparent,
        contentColor = Color.White,
        divider = {
            TabRowDefaults.Divider(
                thickness = 3.dp,
                color = Color.White
            )
        },
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier
                    .pagerTabIndicatorOffset(pagerState, tabPositions)
                    .wrapContentWidth(),
                height = 3.dp,
                color = colorResource(R.color.purple_700)
            )
        },
        edgePadding = 0.dp,
        modifier = Modifier.padding(start = 10.dp)
    ) {
        list.forEachIndexed { index, _ ->
            Tab(
                text = {
                    Text(
                        text = list[index],
                        textAlign = TextAlign.Start,
                        color = if (pagerState.currentPage == index) Color.Black else Color.LightGray,
                        fontFamily = FontFamily(Font(R.font.proximanova_bold)),
                        fontSize = 16.sp,
                    )

                },
                modifier = Modifier
                    .wrapContentWidth(),
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun <T : Any> TabsContent(
    pagerState: PagerState,
    count: Int,
    list: LazyPagingItems<T>,
    navController: NavController
) {
    HorizontalPager(
        count,
        state = pagerState,
        verticalAlignment = Alignment.Top
    ) { page ->
        when (page) {
            0 -> RowSectionItem(list, navController)
            1 -> RowSectionItem(list, navController)
            2 -> RowSectionItem(list, navController)
        }
    }
}


@ExperimentalPagerApi
@Composable
fun <T : Any> RowSectionItem(
    list: LazyPagingItems<T>,
    navController: NavController?
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
                navController?.let { RowItem(posterUrl = item, it) }
            }
        }
    }
}

@Composable
fun RowItem(
    posterUrl: Any?,
    navController: NavController
) {

    //get poster whether its movie or tv media
    val getMediaPosterUrl = posterUrl?.javaClass?.getDeclaredField("posterPath")
    getMediaPosterUrl?.isAccessible = true
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

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    color: Color = Color.White
) {
    var isFavorite by remember { mutableStateOf(false) }

    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = {
            isFavorite = !isFavorite
        }
    ) {
        Icon(
            tint = color,
            imageVector = if (isFavorite) {
                Icons.Filled.Favorite

            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = null
        )
    }
}

@Composable
private fun SectionText(text: String) {
    Text(
        text = text,
        color = colorResource(R.color.purple_700),
        textAlign = TextAlign.Start,
        fontFamily = FontFamily(Font(R.font.proximanova_xbold)),
        fontSize = 24.sp,
        modifier = Modifier
            .padding(start = 16.dp)
    )
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(start = 16.dp, end = 16.dp)
            .clip(shape = RoundedCornerShape(6.dp)),

        elevation = AppBarDefaults.TopAppBarElevation,
        color = Color.LightGray
    ) {
        TextField(
            value = text,
            onValueChange = { onTextChange(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = stringResource(R.string.search),
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize,

                ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    },
                    modifier = Modifier.alpha(ContentAlpha.medium)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color(R.color.purple_700)
            )
        )
    }
}
