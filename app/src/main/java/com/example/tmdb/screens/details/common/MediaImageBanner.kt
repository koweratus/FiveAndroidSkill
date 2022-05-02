package com.example.tmdb.screens.details.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.tmdb.R

@Composable
fun ImageItem(
    mediaPosterUrl: String,
    mediaName: String,
    rating: Float?,
    mediaReleaseDate: String,
    genres: String,
    runTime: String
) {

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
        Box(modifier = Modifier.height(10.dp)) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .crossfade(true)
                        .data(mediaPosterUrl)
                        .build(),
                    filterQuality = FilterQuality.High,
                    contentScale = ContentScale.Crop

                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .fillMaxWidth(1f)
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
            Row(modifier = Modifier.padding(start = 16.dp, top = 105.dp)) {
                rating?.let { CircularProgressionBar(percentage = it) }
                Text(
                    text = stringResource(R.string.user_score),
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.proximanova_bold)),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 16.dp)
                )
            }
            Text(
                text = mediaName,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.proximanova_xbold)),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 160.dp, start = 10.dp)
            )
            Text(
                text = mediaReleaseDate,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.proximanova_regular)),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 190.dp, start = 10.dp)
            )
            Text(
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.proximanova_regular)),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 208.dp, start = 10.dp),
                text = genres + stringResource(R.string.runtime, runTime)
            )
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