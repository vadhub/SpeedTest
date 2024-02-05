package com.abg.speedtest

import SpeedTestTheme
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Request.Builder
import okhttp3.Response
import java.io.BufferedInputStream
import java.io.IOException
import kotlin.math.roundToInt


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SpeedTestTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    //init screen of speed
                    SpeedTestScreen()
                }
            }
        }

        val downloadClient: OkHttpClient = OkHttpClient().newBuilder().build()
        val downloadURL = "http://10.0.2.2:8081/files/search?type=5Mb"
        val download: Request = Builder()
            .url(downloadURL)
            .build()

        downloadClient.newCall(download).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("download", e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val startTime = System.currentTimeMillis()
                val inputStream = response.body!!.byteStream()
                val bis = BufferedInputStream(inputStream)
                var size: Long = 0
                var red = 0
                val buf = ByteArray(1024)
                while (bis.read(buf).also { red = it } != -1) {
                    size += red.toLong()
                }
                val endTime = System.currentTimeMillis()
                var rate = size / 1024 / ((endTime - startTime) / 1000.0) * 8
                rate = (rate * 100.0).roundToInt() / 100.0
                val ratevalue: String = if (rate > 1000) (rate / 1024).toString() + " Mbps" else "${rate.roundToInt()} Kbps"
                inputStream.close()
                bis.close()
                Log.d("download", "download speed = $ratevalue")
            }

        })
    }
}