package com.danielcaballero.composetest.view

import CountryElement
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.danielcaballero.composetest.common.fakeCountry
import com.danielcaballero.composetest.model.local.country_response.CountryResponse
import com.danielcaballero.composetest.ui.util.COUNTRY_CAPITAL_LABEL
import com.danielcaballero.composetest.ui.util.COUNTRY_NAME_LABEL
import com.danielcaballero.composetest.ui.util.DETAILS_CAPITAL
import com.danielcaballero.composetest.ui.util.DETAILS_NAME
import org.junit.Rule
import org.junit.Test


class CountryListViewTest {

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
        onNodeWithTag(DETAILS_NAME).assertTextEquals("N/A")

    }

    @Test
    fun testingElementReactionInNotNullResponse(): Unit = with(composeTestRule) {
        buildCountryDetails(country = fakeCountry)
        onNodeWithTag(DETAILS_CAPITAL).assertTextEquals("Morelia")

    }

    private fun ComposeContentTestRule.buildCountryDetails(country: CountryResponse) {
        setContent {
            CountryElement(country = country)
        }

    }
}