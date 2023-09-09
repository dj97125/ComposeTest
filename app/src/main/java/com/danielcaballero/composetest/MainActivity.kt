package com.danielcaballero.composetest

import CountryListView
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.danielcaballero.composetest.common.ConnectionStatus
import com.danielcaballero.composetest.common.NetworkConnectivityObserver
import com.danielcaballero.composetest.common.NetworkConnectivityObserverImpl
import com.danielcaballero.composetest.ui.theme.ComposeTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var connectivityObserver: NetworkConnectivityObserver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityObserver = NetworkConnectivityObserverImpl(applicationContext)

        setContent {
            ComposeTestTheme(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = true
            ) {
                val network by connectivityObserver.observe()
                    .collectAsState(initial = ConnectionStatus.Unavailable)

                Surface(modifier = Modifier.fillMaxSize()) {
                    CountryListView(networkStatus = network)
                }
            }
        }

    }
}

