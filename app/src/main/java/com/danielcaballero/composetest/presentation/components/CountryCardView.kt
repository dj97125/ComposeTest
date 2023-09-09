package com.danielcaballero.composetest.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.danielcaballero.composetest.domain.CountryDomain
import com.danielcaballero.composetest.ui.theme.Typography
import com.danielcaballero.composetest.ui.util.AutoResizedText
import com.danielcaballero.composetest.ui.util.COUNTRY_CAPITAL_LABEL
import com.danielcaballero.composetest.ui.util.COUNTRY_NAME_LABEL
import com.danielcaballero.composetest.ui.util.DETAILS_CAPITAL
import com.danielcaballero.composetest.ui.util.DETAILS_NAME

@Composable
fun CountryCardView(
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