package com.danielcaballero.composetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.danielcaballero.composetest.common.NetworkConnectivityObserver
import com.danielcaballero.composetest.common.NetworkConnectivityObserverImpl
import com.danielcaballero.composetest.ui.theme.ComposeTestTheme
import com.danielcaballero.composetest.view.Material3AppTheme
import com.danielcaballero.composetest.view_model.AlertDialogs
import com.danielcaballero.composetest.view_model.CountryViewModel
import com.danielcaballero.composetest.view_model.VisibilityComponents
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private lateinit var connectivityObserver: NetworkConnectivityObserver

    private val viewModel: CountryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        connectivityObserver = NetworkConnectivityObserverImpl(applicationContext)

        connectivityObserver.observe().onEach {
            viewModel.networkStatus(status = it)

            viewModel.changeVisibility(
                VisibilityComponents(
                    AlertDialogs.Observer,
                    isVisible = true
                )
            )

        }.launchIn(lifecycleScope)

        setContent {
            ComposeTestTheme {
                Material3AppTheme()
            }
        }
    }
}

