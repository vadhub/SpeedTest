package com.abg.speedtest

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest

@Composable
fun Banner() {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
        BannerAdView(context).apply {
            setAdUnitId("R-M-5893882-1")
            setAdSize(BannerAdSize.stickySize(context, 300))
            val adRequest = AdRequest.Builder().build()
            loadAd(adRequest)
            Log.d("", "$width")
        }
    })
}