package com.abg.speedtest

import SpeedTestTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier

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
    }
}