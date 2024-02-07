package com.abg.speedtest

import SpeedTestTheme
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var uiState = UiState()
        val mutableState = MutableStateFlow(0.0)

        val test = HttpDownloadTest("http://speedtest.tele2.net/10MB.zip") {
            Log.d("33", it.toString())
            mutableState.value = it
        }

        setContent {
            SpeedTestTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    //init screen of speed
                    SpeedTestScreen(mutableState.value) {
                        lifecycleScope.launch(Dispatchers.IO) {
                            test.run()
                            Log.d("1122", test.instantDownloadRate.toString())
                        }
                    }
                }
            }
        }


    }
}
