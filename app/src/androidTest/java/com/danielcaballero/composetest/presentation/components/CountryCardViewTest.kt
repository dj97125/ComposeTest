package com.danielcaballero.composetest.presentation.components

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.danielcaballero.composetest.common.fakeCountry
import com.danielcaballero.composetest.domain.CountryDomain
import com.danielcaballero.composetest.presentation.ui.util.ASSERTING_CAPITAL
import com.danielcaballero.composetest.presentation.ui.util.ASSERTING_NULL
import com.danielcaballero.composetest.presentation.ui.util.COUNTRY_CAPITAL_LABEL
import com.danielcaballero.composetest.presentation.ui.util.COUNTRY_NAME_LABEL
import com.danielcaballero.composetest.presentation.ui.util.DETAILS_CAPITAL
import com.danielcaballero.composetest.presentation.ui.util.DETAILS_NAME
import org.junit.Rule
import org.junit.Test


class CountryCardViewTest {

    @get: Rule
    val composeTestRule = createComposeRule()


    @Test
    fun testingElementsExist(): Unit = with(composeTestRule) {
        buildCountryDetails(country = fakeCountry)
        onNodeWithTag(DETAILS_NAME).assertExists()
        onNodeWithTag(DETAILS_CAPITAL).assertExists()
        onNodeWithTag(COUNTRY_NAME_LABEL).assertExists().assertTextEquals(COUNTRY_NAME_LABEL)
        onNodeWithTag(COUNTRY_CAPITAL_LABEL).assertExists().assertTextEquals(COUNTRY_CAPITAL_LABEL)

    }

    @Test
    fun testingElementReactionInNullResponse(): Unit = with(composeTestRule) {
        buildCountryDetails(country = fakeCountry)
        onNodeWithTag(DETAILS_NAME).assertTextEquals(ASSERTING_NULL)

    }

    @Test
    fun testingElementReactionInNotNullResponse(): Unit = with(composeTestRule) {
        buildCountryDetails(country = fakeCountry)
        onNodeWithTag(DETAILS_CAPITAL).assertTextEquals(ASSERTING_CAPITAL)

    }

    private fun ComposeContentTestRule.buildCountryDetails(
        country: CountryDomain,
    ) {
        setContent {
            CountryCardView(
                country = country
            )
        }

    }
}