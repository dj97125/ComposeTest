package com.danielcaballero.composetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.danielcaballero.composetest.ui.theme.ComposeTestTheme
import com.danielcaballero.composetest.view.Material3AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeTestTheme {
                Material3AppTheme()
            }
        }
    }
}

