package com.danielcaballero.composetest.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danielcaballero.composetest.common.ConnectionStatus
import com.danielcaballero.composetest.common.StateAction
import com.danielcaballero.composetest.domain.CountryDomain
import com.danielcaballero.composetest.ui.util.AlertDialog
import com.danielcaballero.composetest.ui.util.LoadingAnimation
import com.danielcaballero.composetest.view_model.CountryViewModel

@Composable
fun CountryListViewPortrait(
    state: StateAction,
    viewModel: CountryViewModel,
    networkStatus: ConnectionStatus,
    modifier: Modifier = Modifier
) {
    val visibility by viewModel.visibilityFlow.collectAsState(initial = false)


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Network ${networkStatus.name}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .size(50.dp)
                    .background(MaterialTheme.colors.primary)
                    .padding(15.dp)

            )

            when (state) {
                is StateAction.Succes<*> -> {
                    val retrievedElements = state.respoonse as List<CountryDomain>
                    ListOfElements(country = retrievedElements, modifier = modifier)

                    if (visibility)
                        AlertDialog(
                            title = "SUCCESS",
                            body = "Welcome to your country list",
                            modifier = modifier
                        )


                }

                is StateAction.Errror -> {

                    if (visibility)
                        AlertDialog(
                            onRetry = { viewModel.getCountries() },
                            title = "ERROR",
                            body = "Something went wrong",
                            modifier = modifier
                        )


                }

                StateAction.Loading -> LoadingAnimation()
                else -> {}
            }
        }


    }
}