package com.danielcaballero.composetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.danielcaballero.composetest.common.ConnectionStatus
import com.danielcaballero.composetest.common.NetworkConnectivityObserver
import com.danielcaballero.composetest.common.NetworkConnectivityObserverImpl
import com.danielcaballero.composetest.ui.theme.ComposeTestTheme
import com.danielcaballero.composetest.view.Material3AppTheme
import com.danielcaballero.composetest.view_model.CountryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    private val viewModel: CountryViewModel by viewModels()
//    private lateinit var connectivityObserver: NetworkConnectivityObserver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        connectivityObserver = NetworkConnectivityObserverImpl(applicationContext)
//
//        connectivityObserver.observe().onEach {
//            if (it == ConnectionStatus.Available) {
//                viewModel.networkStatus(isConnected = true)
//            }
//        }.launchIn(lifecycleScope)

        setContent {
            ComposeTestTheme {
                Material3AppTheme()
            }
        }
    }
}

