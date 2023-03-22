package com.kova.tmdb.ads

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun AdBanner() {
    AndroidView(
        factory = { context ->
            AdView(context).apply {
                adUnitId = "ca-app-pub-4829215222182224/6745271478"
            }
        },
        update = { view ->
            view.setAdSize(AdSize.BANNER)
            view.loadAd(AdRequest.Builder().build())
        }
    )
}
