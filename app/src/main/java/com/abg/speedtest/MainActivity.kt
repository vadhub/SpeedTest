package com.abg.speedtest

import SpeedTestTheme
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mutableState = MutableStateFlow(0.0)
        val mutableStatePing = MutableStateFlow(0.0)
        val mutableStateIsProgress = MutableStateFlow(false)

        val test = HttpDownloadTest("http://speedtest.tele2.net/10MB.zip") {
            mutableState.value = it
        }

        setContent {
            SpeedTestTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {

                    val speed = mutableState.collectAsState()
                    val ping = mutableStatePing.collectAsState()
                    val isEnables = mutableStateIsProgress.collectAsState()

                    SpeedTestScreen(speed.value, ping.value, !isEnables.value) {
                        lifecycleScope.launch(Dispatchers.IO) {
                            mutableStatePing.value = Ping.getPingTime("www.google.com")
                            mutableStateIsProgress.value = true
                            test.run()
                            mutableStateIsProgress.value = false
                        }
                    }
                }
            }
        }


    }
}
