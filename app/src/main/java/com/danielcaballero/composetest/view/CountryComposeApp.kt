package com.danielcaballero.composetest.view

import CountryListView
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.danielcaballero.composetest.ui.theme.ComposeTestTheme
import com.danielcaballero.composetest.view_model.CountryViewModel

@Composable
fun Material3AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
) {
    val countryViewModel = hiltViewModel<CountryViewModel>()
    ComposeTestTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),

        ) {
            CountryListView(viewModel = countryViewModel )

        }
    }
}