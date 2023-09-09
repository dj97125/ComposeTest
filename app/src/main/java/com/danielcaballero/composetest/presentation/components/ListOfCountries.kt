package com.danielcaballero.composetest.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.danielcaballero.composetest.domain.CountryDomain

@Composable
fun ListOfElements(country: List<CountryDomain>, modifier: Modifier) {

    LazyColumn(modifier = modifier) {
        country.forEach { country ->
            item {
                CountryCardView(country, modifier = modifier)
            }
        }
    }


}