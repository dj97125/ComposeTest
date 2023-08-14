import android.content.Context
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.danielcaballero.composetest.common.ConnectionStatus
import com.danielcaballero.composetest.common.StateAction
import com.danielcaballero.composetest.domain.CountryDomain
import com.danielcaballero.composetest.ui.theme.Typography
import com.danielcaballero.composetest.ui.util.AlertNotification
import com.danielcaballero.composetest.ui.util.AlertRetry
import com.danielcaballero.composetest.ui.util.AutoResizedText
import com.danielcaballero.composetest.ui.util.COUNTRY_CAPITAL_LABEL
import com.danielcaballero.composetest.ui.util.COUNTRY_NAME_LABEL
import com.danielcaballero.composetest.ui.util.DETAILS_CAPITAL
import com.danielcaballero.composetest.ui.util.DETAILS_NAME
import com.danielcaballero.composetest.ui.util.LoadingAnimation
import com.danielcaballero.composetest.view_model.AlertDialogs
import com.danielcaballero.composetest.view_model.CountryViewModel
import com.danielcaballero.composetest.view_model.VisibilityComponents


@Composable
fun CountryListView(viewModel: CountryViewModel, context: Context = LocalContext.current) {

    val country by viewModel.countryResponse.collectAsStateWithLifecycle()

    val networkStatus = viewModel.networkStatus

    val countryResponseDialogIsVisible = viewModel.isVisibleCountryResponseAlert
    val networkObserverDialogIsVisible = viewModel.isVisibleNetworkObserverAlert



    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {


        when (networkStatus) {

            ConnectionStatus.Lost -> {
                AlertNotification(
                    isVisible = networkObserverDialogIsVisible,
                    title = "ALERT",
                    body = "No connection",
                    onDismiss = {
                        viewModel.changeVisibility(
                            VisibilityComponents(
                                AlertDialogs.Observer,
                                isVisible = false
                            )
                        )
                    }
                )
            }


            ConnectionStatus.Available -> {
                AlertNotification(
                    isVisible = networkObserverDialogIsVisible,
                    title = "ALERT",
                    body = "Back online",
                    onDismiss = {
                        viewModel.changeVisibility(
                            VisibilityComponents(
                                AlertDialogs.Observer,
                                isVisible = false
                            )
                        )
                    }
                )

            }

            else -> {}
        }



        when (val state = country) {
            is StateAction.Succes<*> -> {
                val retrievedElements = state.respoonse as List<CountryDomain>
                ListOfElements(country = retrievedElements)

                AlertNotification(
                    isVisible = countryResponseDialogIsVisible,
                    title = "SUCCESS",
                    body = "Welcome to your country list",
                    onDismiss = {
                        viewModel.changeVisibility(
                            VisibilityComponents(
                                AlertDialogs.CountryResponse,
                                isVisible = false
                            )
                        )
                    }
                )


            }

            is StateAction.Errror -> {

                AlertRetry(
                    isVisible = countryResponseDialogIsVisible,
                    onDismiss = {
                        viewModel.changeVisibility(
                            VisibilityComponents(
                                AlertDialogs.CountryResponse,
                                isVisible = false
                            )
                        )
                    },
                    onRetry = { viewModel.getCountries() },
                    title = "ERROR",
                    body = "Something went wrong"
                )


            }

            StateAction.Loading -> LoadingAnimation()
            else -> {}
        }


    }
}


@Composable
fun ListOfElements(country: List<CountryDomain>, modifier: Modifier = Modifier) {

    LazyColumn(modifier = modifier.fillMaxSize()) {
        country.forEach { country ->
            item {
                CountryElement(country, modifier = modifier)
            }
        }
    }


}

@Composable
fun CountryElement(
    country: CountryDomain,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(3.dp, Color.Black),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Column(
            modifier = modifier.weight(.5f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Spacer(modifier = modifier.size(10.dp))
                AutoResizedText(
                    text = COUNTRY_NAME_LABEL,
                    modifier = modifier
                        .weight(.5f)
                        .testTag(COUNTRY_NAME_LABEL),
                    textAlign = TextAlign.Start,
                    style = Typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                AutoResizedText(
                    text = country.name ?: "N/A",
                    modifier = modifier
                        .weight(.5f)
                        .testTag(DETAILS_NAME),
                    textAlign = TextAlign.Justify,
                    style = Typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
        Column(
            modifier = modifier.weight(.5f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Spacer(modifier = modifier.size(10.dp))
                AutoResizedText(
                    text = COUNTRY_CAPITAL_LABEL,
                    modifier = modifier
                        .weight(.5f)
                        .testTag(COUNTRY_CAPITAL_LABEL),
                    textAlign = TextAlign.Start,
                    style = Typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                AutoResizedText(
                    text = country.capital ?: "N/A",
                    modifier = modifier
                        .weight(.5f)
                        .testTag(DETAILS_CAPITAL),
                    textAlign = TextAlign.Justify,
                    style = Typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }

}

