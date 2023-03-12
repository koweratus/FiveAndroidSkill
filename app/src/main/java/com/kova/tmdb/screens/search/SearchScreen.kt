package com.example.tmdb.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.tmdb.navigation.RootScreen
import com.example.tmdb.utils.Constants

@Composable
fun SearchScreen(navController: NavController) {
    val viewModel: SearchViewModel = hiltViewModel()
    Surface(
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            topBar = { SearchAppBar() }
        ) {
            Box {
                SearchList(viewModel, navController = navController)
            }
        }
    }
}

@Composable
private fun SearchList(
    viewModel: SearchViewModel,
    navController: NavController
) {
    val actorsList = viewModel.searchSearch.value.collectAsLazyPagingItems()
    LazyColumn(
    ) {

        items(actorsList) { item ->
            (item?.name ?: item?.title)?.let {
                item?.id?.let { it1 ->
                    if (item?.mediaType == "tv") {
                        SearchItem(
                            title = it,
                            photo = "${Constants.IMAGE_BASE_UR}/${item?.posterPath}",
                            navController = navController,
                            id = it1,
                            route = RootScreen.TvDetails.route
                        )
                    } else {
                        SearchItem(
                            title = it,
                            photo = "${Constants.IMAGE_BASE_UR}/${item?.posterPath}",
                            navController = navController,
                            id = it1,
                            route = RootScreen.MovieDetails.route
                        )
                    }

                }
            }

        }
    }
}

@Composable
fun SearchAppBar() {
    val viewModel: SearchViewModel = hiltViewModel()

    var query: String by rememberSaveable { mutableStateOf("") }
    var showClearIcon by rememberSaveable { mutableStateOf(false) }

    if (query.isEmpty()) {
        showClearIcon = false
    } else if (query.isNotEmpty()) {
        showClearIcon = true
    }

    TextField(
        value = query,
        onValueChange = { onQueryChanged ->
            // If user makes changes to text, immediately updated it.
            query = onQueryChanged
            // To avoid crash, only query when string isn't empty.
            if (onQueryChanged.isNotEmpty()) {
                // Pass latest query to refresh search results.
                viewModel.searchAll(onQueryChanged)
            }
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                tint = MaterialTheme.colors.onPrimary,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            if (showClearIcon) {
                IconButton(onClick = { query = "" }) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        tint = MaterialTheme.colors.onPrimary,
                        contentDescription = "Clear Icon"
                    )
                }
            }
        },
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
        placeholder = { Text(text = "Search for media...") },
        textStyle = MaterialTheme.typography.subtitle1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.background, shape = RectangleShape)
    )
}

@Composable
fun SearchItem(
    title: String,
    photo: String,
    id: Int,
    route: String,
    navController: NavController
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                horizontal = dimensionResource(id = com.example.tmdb.R.dimen.xsmall_padding),
                vertical = dimensionResource(id = com.example.tmdb.R.dimen.xsmall_padding)
            )
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(bounded = true, color = Color.Black),
                onClick = {
                    navController.navigate("$route/${id}")
                }
            ),
        shape = RoundedCornerShape(dimensionResource(id = com.example.tmdb.R.dimen.shaped_corners)),
        elevation = dimensionResource(id = com.example.tmdb.R.dimen.shaped_corners_big),
        backgroundColor = Color.White
    ) {
        Row(Modifier.wrapContentHeight()) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .crossfade(true)
                        .data(photo)
                        .build(),
                    filterQuality = FilterQuality.High,
                    contentScale = ContentScale.Crop

                ),
                contentDescription = null,
                modifier = Modifier
                    .width(dimensionResource(id = com.example.tmdb.R.dimen.padding_80))
                    .height(dimensionResource(id = com.example.tmdb.R.dimen.padding_80))
                    .clip(shape = RoundedCornerShape(dimensionResource(id = com.example.tmdb.R.dimen.shaped_corners))),
                contentScale = ContentScale.Crop,

                )
            Text(
                modifier = Modifier
                    .padding(
                        top = dimensionResource(id = com.example.tmdb.R.dimen.xsmall_padding),
                        start = dimensionResource(id = com.example.tmdb.R.dimen.small_padding),
                        end = dimensionResource(id = com.example.tmdb.R.dimen.xsmall_padding)
                    ),
                text = title,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(com.example.tmdb.R.font.proximanova_bold)),
                color = Color.Black,
                textAlign = TextAlign.Center,
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
