package com.application.optimization

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.application.optimization.presentation.navigation.NavigationApp
import com.application.optimization.ui.theme.ApplicationOptimizationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ApplicationOptimizationTheme {
                NavigationApp()
            }
        }
    }
}