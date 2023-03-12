package com.example.tmdb.screens.details.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.tmdb.R
import com.example.tmdb.remote.responses.ReviewResponse
import com.example.tmdb.screens.widgets.getAbbreviatedFromDateTime
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

@ExperimentalPagerApi
@Composable
fun TabsContentForSocial(pagerState: PagerState, count: Int, review: ReviewResponse?) {

    HorizontalPager(count, state = pagerState, verticalAlignment = Alignment.Top) { page ->
        when (page) {
            0 -> ReviewSection(review)
            1 -> ReviewSection(review)
        }

    }
}

@Composable
private fun ReviewSection(
    review: ReviewResponse?,
) {

    LazyColumn(modifier = Modifier.height(dimensionResource(id = R.dimen.height_xxl)))
    {
        review?.review?.forEach {
            item {
                val date = getAbbreviatedFromDateTime(it.createdAt, "yyyy-MM-dd", "MMM d, YYYY")
                val avatar = it.authorDetails.avatarPath
                Row(
                    modifier = Modifier
                        .fillMaxHeight(0.3f)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(context = LocalContext.current)
                                .crossfade(true)
                                .data(avatar?.drop(1) ?: avatar)
                                .build(),
                            filterQuality = FilterQuality.High,
                            contentScale = ContentScale.Crop

                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxHeight(1f)
                            .height(dimensionResource(id = R.dimen.height_medium))
                            .width(dimensionResource(id = R.dimen.width_medium))
                            .padding(
                                start = dimensionResource(id = R.dimen.small_medium_padding),
                                top = dimensionResource(id = R.dimen.small_medium_padding)
                            )
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                    ) {
                        Text(
                            text = stringResource(R.string.reviewBy, it.author),
                            color = Color.Black,
                            textAlign = TextAlign.Start,
                            fontFamily = FontFamily(Font(R.font.proximanova_xbold)),
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(
                                    start = dimensionResource(id = R.dimen.small_padding),
                                    top = dimensionResource(id = R.dimen.small_medium_padding)
                                )
                        )
                        Text(
                            text = stringResource(R.string.writtenBy, it.author, date!!),
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.proximanova_regular)),
                            fontSize = 14.sp,
                            modifier = Modifier
                                .padding(
                                    start = dimensionResource(id = R.dimen.small_padding),
                                    top = dimensionResource(id = R.dimen.xsmall_padding)
                                )
                                .fillMaxWidth(.8f),
                            softWrap = true,
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(dimensionResource(id = R.dimen.height_xxl))
                ) {
                    Text(
                        text = it.content,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.proximanova_regular)),
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(
                                start = dimensionResource(id = R.dimen.small_padding),
                                top = dimensionResource(id = R.dimen.xsmall_padding)
                            )
                            .fillMaxWidth(.8f),
                        softWrap = true,
                    )
                    Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.big_padding)))
                }
            }
        }
    }
}

@Composable
fun SocialError() {
    Text(
        text = "No review available",
        color = colorResource(R.color.purple_200),
        textAlign = TextAlign.Start,
        fontFamily = FontFamily(Font(R.font.proximanova_medium)),
        fontSize = 18.sp,
        modifier = Modifier
            .padding(start = dimensionResource(id = R.dimen.small_padding))
    )
}
