package com.example.tmdb.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.tmdb.BottomBarScreen
import com.example.tmdb.R
import com.example.tmdb.model.Movie
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState


val boldRegex = Regex("(?<!\\*)\\*\\*(?!\\*).*?(?<!\\*)\\*\\*(?!\\*)")

@ExperimentalPagerApi
@Composable
fun DetailViewScreen(navController: NavController) {
    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .padding(top = 53.dp)
    ) {
        item {
            ImageItem()
            Overview(navController)
        }
    }
}

@Composable
private fun ImageItem() {

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
        Box(modifier = Modifier.height(100.dp)) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .crossfade(true)
                        .data("https://m.media-amazon.com/images/M/MV5BNGVjNWI4ZGUtNzE0MS00YTJmLWE0ZDctN2ZiYTk2YmI3NTYyXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_.jpg")
                        .build(),
                    filterQuality = FilterQuality.High,
                    contentScale = ContentScale.Crop

                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
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
            Row(modifier = Modifier.padding(start = 16.dp,top=105.dp)) {
                CircularProgressionBar(percentage = 0.76f, number = 100)
                Text(
                    text = "User Score",
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.proximanova_bold)),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding( top= 10.dp,start = 16.dp)
                )
            }
            Text(
                text = "Iron Man (2008)",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.proximanova_xbold)),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 160.dp, start = 10.dp)
            )
            Text(
                text = "05/02/2008 (US)",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.proximanova_regular)),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 190.dp, start = 10.dp)
            )
            CustomText(text = "Action, Science Fiction, Adventure **2h 6m** ")

            Icon(
                imageVector =
                Icons.Default.FavoriteBorder,
                contentDescription = null,
                modifier = Modifier.padding(top = 250.dp, start = 10.dp),
                tint = Color.White
            )
        }

    }

}


@Composable
fun CustomText(text: String, modifier: Modifier = Modifier) {

    var results: MatchResult? = boldRegex.find(text)
    val boldIndexes = mutableListOf<Pair<Int, Int>>()
    val keywords = mutableListOf<String>()
    var finalText = text

    while (results != null) {
        keywords.add(results.value)
        results = results.next()
    }

    keywords.forEach { keyword ->
        val indexOf = finalText.indexOf(keyword)
        val newKeyWord = keyword.removeSurrounding("**")
        finalText = finalText.replace(keyword, newKeyWord)
        boldIndexes.add(Pair(indexOf, indexOf + newKeyWord.length))
    }

    val annotatedString = buildAnnotatedString {
        append(finalText)

        // Add bold style to keywords that has to be bold
        boldIndexes.forEach {
            addStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 14.sp

                ),
                start = it.first,
                end = it.second
            )

        }
    }

    Text(
        color = Color.White,
        fontFamily = FontFamily(Font(R.font.proximanova_regular)),
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(top = 208.dp, start = 10.dp),
        text = annotatedString
    )
}

@ExperimentalPagerApi
@Composable
private fun Overview(navController: NavController) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        val pagerState = rememberPagerState(initialPage = 0)
        val listFirstTab =
            listOf(stringResource(R.string.reviews), stringResource(R.string.discussions))
        SectionTitle("Overview")
        Text(
            text = "After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.",
            color = Color.Black,
            textAlign = TextAlign.Justify,
            fontFamily = FontFamily(Font(R.font.proximanova_regular)),
            fontSize = 14.sp,
            modifier = Modifier
                .padding(start = 16.dp, top = 10.dp)
                .fillMaxWidth(.8f),
            softWrap = true
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Don Heck",
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.proximanova_bold)),
                fontSize = 14.sp
            )
            Text(
                text = "Jack Kirby",
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.proximanova_bold)),
                fontSize = 14.sp
            )
            Text(
                text = "Jon Favreau",
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.proximanova_bold)),
                fontSize = 14.sp
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Characters",
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.proximanova_regular)),
                fontSize = 14.sp
            )
            Text(
                text = "Characters",
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.proximanova_regular)),
                fontSize = 14.sp
            )
            Text(
                text = "Director",
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.proximanova_regular)),
                fontSize = 14.sp
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Don Heck",
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.proximanova_bold)),
                fontSize = 14.sp
            )
            Text(
                text = "Jack Macrum",
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.proximanova_bold)),
                fontSize = 14.sp
            )
            Text(
                text = "Matt Holloway ",
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.proximanova_bold)),
                fontSize = 14.sp
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Screenplay",
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.proximanova_regular)),
                fontSize = 14.sp
            )
            Text(
                text = "Screenplay",
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.proximanova_regular)),
                fontSize = 14.sp
            )
            Text(
                text = "Screenplay",
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.proximanova_regular)),
                fontSize = 14.sp
            )
        }
        SectionTitle("Top Billed Cast")
        TopBilledCastSectionItem(list = emptyList(),navController)
        Spacer(Modifier.padding(35.dp))
        SectionTitle("Social")
        Tabs(pagerState = pagerState, listFirstTab)
        TabsContentForSocial(pagerState = pagerState, listFirstTab.size)
        SectionTitle(title = "Recommendations")
        RowRecommendationsItem(list = emptyList() ,navController)
        Spacer(Modifier.padding(35.dp))

    }


}


@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        color = colorResource(R.color.purple_700),
        textAlign = TextAlign.Start,
        fontFamily = FontFamily(Font(R.font.proximanova_xbold)),
        fontSize = 20.sp,
        modifier = Modifier
            .padding(start = 16.dp, top = 35.dp)
    )
}

@ExperimentalPagerApi
@Composable
fun TabsContentForSocial(pagerState: PagerState, count: Int) {

    HorizontalPager(count, state = pagerState, verticalAlignment = Alignment.Top) { page ->
        when (page) {
            0 -> ReviewSection()
            1 -> ReviewSection()
        }

    }

}

@Composable
private fun ReviewSection() {
    Row(
        modifier = Modifier
            .fillMaxHeight(1f)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .crossfade(true)
                    .data("https://m.media-amazon.com/images/M/MV5BNGVjNWI4ZGUtNzE0MS00YTJmLWE0ZDctN2ZiYTk2YmI3NTYyXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_.jpg")
                    .build(),
                filterQuality = FilterQuality.High,
                contentScale = ContentScale.Crop

            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight(1f)
                .height(100.dp)
                .width(100.dp)
                .padding(start = 10.dp, top = 10.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(256.dp)
        ) {
            Text(
                text = "A review by The peruvian post",
                color = Color.Black,
                textAlign = TextAlign.Start,
                fontFamily = FontFamily(Font(R.font.proximanova_xbold)),
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(start = 16.dp, top = 10.dp)
            )
            Text(
                text = "Written by The Peruvian Post on February 17, 2020",
                color = Color.Black,
                textAlign = TextAlign.Justify,
                fontFamily = FontFamily(Font(R.font.proximanova_regular)),
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(start = 16.dp, top = 5.dp)
                    .fillMaxWidth(.8f),
                softWrap = true,
            )
        }


    }
    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(340.dp)
            .padding(top = 115.dp)
    ) {
        Text(
            text = "orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            color = Color.Black,
            textAlign = TextAlign.Justify,
            fontFamily = FontFamily(Font(R.font.proximanova_regular)),
            fontSize = 14.sp,
            modifier = Modifier
                .padding(start = 16.dp, top = 5.dp)
                .fillMaxWidth(.8f),
            softWrap = true,
        )
    }
}


@ExperimentalPagerApi
@Composable
fun RowRecommendationsItem(
    list: List<Movie>,
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
                items = list
            ) { item ->
                RowItemRecommendations(
                    moviesData = item,
                    navController = navController
                )
            }
        }
    }
}

@Composable
private fun RowItemRecommendations(
    moviesData: Movie,
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
                    onClick = {// navController.navigate(BottomBarScreen.Details.route)
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
                            .data(moviesData.posterPath)
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
            text = moviesData.backdropPath,
            color = colorResource(R.color.purple_700),
            textAlign = TextAlign.Start,
            fontFamily = FontFamily(Font(R.font.proximanova_bold)),
            fontSize = 16.sp,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
        )
    }

}

@Composable
fun CircularProgressionBar(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 9.sp,
    radius: Dp = 40.dp,
    color: Color = Color.Green,
    strokeWidth: Dp = 3.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius)
    ) {
        Canvas(modifier = Modifier.size(radius)) {
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * curPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = (curPercentage.value * number).toInt().toString() + "%",
            color = Color.White,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }

}

@Composable
fun TopBilledCastItem(
    moviesData: Movie,
    navController: NavController
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
                        .data(moviesData.posterPath)
                        .build(),
                    filterQuality = FilterQuality.High,
                    contentScale = ContentScale.Crop

                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth().height(140.dp)
                    .clip(shape = RoundedCornerShape(6.dp)),
                contentScale = ContentScale.Crop,

                )
            Text(
                modifier = Modifier
                    .padding(top = 6.dp, start = 6.dp, end = 6.dp),
                text = moviesData.title,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.proximanova_xbold)),
                color = Color.Black
            )
            Text(
                modifier = Modifier
                    .padding(top = 6.dp, start = 6.dp, end = 6.dp),
                text = moviesData.backdropPath,
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
    list: List<Movie>,
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
                navController?.let { TopBilledCastItem(moviesData = item, it) }
            }
        }
    }
}