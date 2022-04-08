package com.example.tmdb.screens.details.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tmdb.R
import com.example.tmdb.remote.responses.CreditsResponse
import com.example.tmdb.screens.*
import com.example.tmdb.screens.widgets.SectionText
import com.example.tmdb.utils.Resource
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@ExperimentalPagerApi
@Composable
fun Overview(
    navController: NavController,
    overview: String,
    casts: Resource<CreditsResponse>
) {
    val pagerState = rememberPagerState(initialPage = 0)
    val listFirstTab =
        listOf(stringResource(R.string.reviews), stringResource(R.string.discussions))
    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.height(300.dp)
    ) {

        item{
            SectionText("Overview")
            Text(
                text = overview,
                color = Color.Black,
                textAlign = TextAlign.Justify,
                fontFamily = FontFamily(Font(R.font.proximanova_regular)),
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(start = 16.dp, top = 10.dp)
                    .fillMaxWidth(.8f),
                softWrap = true
            )
        }
        item{
            if (casts is Resource.Success){
                CastDetails(casts.data!!)
        }
        }


    }


}