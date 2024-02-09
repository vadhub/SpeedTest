package com.abg.speedtest

import SpeedTestTheme
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import kotlin.math.max


class MainActivity : ComponentActivity(), Listener {

    private val mutableState = MutableStateFlow(0.0)
    private val mutableSpeedMax = MutableStateFlow(0.0)

    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val mutableStatePing = MutableStateFlow(0.0)
        val mutableStateIsProgress = MutableStateFlow(false)


        val test = HttpDownloadTest("http://speedtest.tele2.net/100MB.zip", this)

        setContent {
            SpeedTestTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {

                    val speed = mutableState.collectAsState()
                    val ping = mutableStatePing.collectAsState()
                    val isEnables = mutableStateIsProgress.collectAsState()
                    val maxSpeed = mutableSpeedMax.collectAsState()

                    SpeedTestScreen(speed.value, maxSpeed.value, ping.value, !isEnables.value) {
                        lifecycleScope.launch(Dispatchers.IO) {
                            mutableStateIsProgress.value = true
                            mutableStatePing.value = Ping.getPingTime("www.google.com")
                            test.run()
                            mutableStateIsProgress.value = false

                        }
                    }
                }
            }
        }


    }

    override fun getSpeed(speed: Double) {
        mutableState.value = speed
        mutableSpeedMax.value = max(mutableSpeedMax.value, speed)
    }
}
