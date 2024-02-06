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
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Request.Builder
import okhttp3.Response
import java.io.IOException


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

        val request: Request = Builder()
            .url("https://publicobject.com/helloworld.txt")
            .build()

        val client: OkHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(Interceptor { chain: Interceptor.Chain ->
                val originalResponse = chain.proceed(chain.request())
                originalResponse.newBuilder()
                    .body(ProgressResponseBody(originalResponse.body, progressListener))
                    .build()
            })
            .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                println(e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                println(true)
            }

        })

    }

    val progressListener: ProgressListener = object : ProgressListener {
        var firstUpdate = true
        override fun update(bytesRead: Long, contentLength: Long, done: Boolean) {

            if (done) {
                println("completed")
            } else {
                if (firstUpdate) {
                    firstUpdate = false
                    if (contentLength == -1L) {
                        println("content-length: unknown")
                    } else {
                        System.out.format("content-length: %d\n", contentLength)
                    }
                }
                println(bytesRead)
                if (contentLength != -1L) {
                    Log.d("dddd", String.format("%d%% done\n", 100 * bytesRead / contentLength))
                }
            }
        }
    }

//    private fun measureInternetSpeed() {
//        val downloadClient: OkHttpClient = OkHttpClient().newBuilder().build()
//        val request = Builder()
//            .url("http://speedtest.tele2.net/100MB.zip") // Пример URL для скачивания 100МБ файла в тестовых целях
//            .build();
//
//        // Запускаем запрос и измеряем время
//        val startTime = System.currentTimeMillis();
//        downloadClient.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (!response.isSuccessful) {
//                    throw IOException("Unexpected code $response");
//                }
//
//                val endTime = System.currentTimeMillis();
//                val totalTime = endTime - startTime;
//                val fileSize = response.body!!.contentLength();
//
//                // Вычисляем скорость передачи данных
//                val downloadSpeed =
//                    (fileSize / 1024) / (totalTime / 1000); // в килобайтах в секунду
//
//                // Обновляем UI с результатами измерения скорости
//
//                Log.d("test", String.format("Скорость: %.2f КБ/с", downloadSpeed.toFloat()))
//
//            }
//
//        });
//    }
}