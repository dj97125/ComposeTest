package com.danielcaballero.composetest.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danielcaballero.composetest.common.ConnectionStatus
import com.danielcaballero.composetest.common.StateAction
import com.danielcaballero.composetest.domain.CountryDomain
import com.danielcaballero.composetest.presentation.ui.util.ALERT_DIALOG
import com.danielcaballero.composetest.presentation.ui.util.AlertDialog
import com.danielcaballero.composetest.presentation.ui.util.COUNTRY_LIST_VIEW_LANDSCAPE
import com.danielcaballero.composetest.presentation.ui.util.LIST_OF_ELEMENTS
import com.danielcaballero.composetest.presentation.ui.util.LOADING_ANIMATION
import com.danielcaballero.composetest.presentation.ui.util.LoadingAnimation
import com.danielcaballero.composetest.presentation.ui.util.NETWORK_LABEL
import com.danielcaballero.composetest.view_model.CountryViewModel

@Composable
fun CountryListViewLandscape(
    state: StateAction,
    viewModel: CountryViewModel,
    networkStatus: ConnectionStatus,
    modifier: Modifier = Modifier
) {

    val visibility by viewModel.visibilityFlow.collectAsState(initial = false)



    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag(COUNTRY_LIST_VIEW_LANDSCAPE)
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
                    .testTag(NETWORK_LABEL)

            )

            when (state) {
                is StateAction.Succes<*> -> {
                    val retrievedElements = state.respoonse as List<CountryDomain>
                    ListOfElements(
                        country = retrievedElements,
                        modifier = modifier.testTag(LIST_OF_ELEMENTS)
                    )

                    if (visibility)
                        AlertDialog(
                            title = "SUCCESS",
                            body = "Welcome to your country list",
                            modifier = modifier
                        )


                }

                is StateAction.Errror -> {
                    AlertDialog(
                        onRetry = { viewModel.getCountries() },
                        title = "ERROR",
                        body = "Something went wrong",
                        modifier = modifier.testTag(ALERT_DIALOG)
                    )


                }

                StateAction.Loading -> LoadingAnimation(modifier = modifier.testTag(
                    LOADING_ANIMATION))
                else -> {}
            }
        }


    }
}