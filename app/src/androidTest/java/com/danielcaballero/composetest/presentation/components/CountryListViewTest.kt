package com.danielcaballero.composetest.presentation.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.danielcaballero.composetest.common.ConnectionStatus
import com.danielcaballero.composetest.common.StateAction
import com.danielcaballero.composetest.common.fakeCountryList
import com.danielcaballero.composetest.presentation.ui.util.ALERT_DIALOG
import com.danielcaballero.composetest.presentation.ui.util.ASSERTING_CONNECTION_STATUS_AVAILABLE
import com.danielcaballero.composetest.presentation.ui.util.ASSERTING_CONNECTION_STATUS_LOST
import com.danielcaballero.composetest.presentation.ui.util.ASSERTING_CONNECTION_STATUS_UNAVAILABLE
import com.danielcaballero.composetest.presentation.ui.util.COUNTRY_LIST_VIEW_LANDSCAPE
import com.danielcaballero.composetest.presentation.ui.util.DISMISS_BUTTON
import com.danielcaballero.composetest.presentation.ui.util.LIST_OF_ELEMENTS
import com.danielcaballero.composetest.presentation.ui.util.LOADING_ANIMATION
import com.danielcaballero.composetest.presentation.ui.util.NETWORK_LABEL
import com.danielcaballero.composetest.presentation.ui.util.ON_ERROR_ALERT_DIALOG_MESSSAGE
import com.danielcaballero.composetest.use_cases.GetCountryUseCase
import com.danielcaballero.composetest.view_model.CountryViewModel
import io.ktor.client.engine.cio.FailToConnectException
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.CoroutineExceptionHandler
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class CountryListViewLandscapeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @MockK
    lateinit var viewModel: CountryViewModel

    @MockK
    lateinit var getCountryUseCase: GetCountryUseCase


    @get:Rule
    val mockkRule = MockKRule(this)

    private val exceptions = mutableListOf<Throwable>()


    val handler = CoroutineExceptionHandler { _, throwable ->
        exceptions.add(throwable)
    }


    @Before
    fun setup() {
        viewModel = CountryViewModel(
            handler = handler,
            getCountryUseCase = getCountryUseCase
        )
    }

    @Test
    fun testingElementExistWithSUCCESSstateAndNetworkLabelUnavailable(): Unit =
        with(composeTestRule) {

            buildCountryListViewLandscape(
                networkStatus = ConnectionStatus.Unavailable, stateAction = StateAction.Succes(
                    fakeCountryList, "200"
                )
            )

            onNodeWithTag(COUNTRY_LIST_VIEW_LANDSCAPE).assertExists().assertIsDisplayed()
            onNodeWithTag(NETWORK_LABEL).assertExists().assertIsDisplayed()
                .assertTextEquals(ASSERTING_CONNECTION_STATUS_UNAVAILABLE)
            onAllNodesWithTag(LIST_OF_ELEMENTS).onFirst().assertExists().assertIsDisplayed()

        }

    @Test
    fun testingElementExistWithERRORstateAndNetworkLabelAvailable(): Unit =
        with(composeTestRule) {

            buildCountryListViewLandscape(
                networkStatus = ConnectionStatus.Available,
                stateAction = StateAction.Errror(FailToConnectException())
            )

            onNodeWithTag(COUNTRY_LIST_VIEW_LANDSCAPE).assertExists().assertIsDisplayed()
            onNodeWithTag(NETWORK_LABEL).assertExists().assertIsDisplayed()
                .assertTextEquals(ASSERTING_CONNECTION_STATUS_AVAILABLE)
            onAllNodesWithTag(ALERT_DIALOG).onFirst().assertExists().assertIsDisplayed()
            onNodeWithText(ON_ERROR_ALERT_DIALOG_MESSSAGE).assertExists()
            onNodeWithText(DISMISS_BUTTON).performClick()
            onAllNodesWithTag(ALERT_DIALOG).onFirst().assertDoesNotExist()


        }

    @Test
    fun testingElementExistWithERRORstateRetryClicked(): Unit =
        with(composeTestRule) {

            buildCountryListViewLandscape(
                networkStatus = ConnectionStatus.Available,
                stateAction = StateAction.Errror(FailToConnectException())
            )


            onAllNodesWithTag(ALERT_DIALOG).onFirst().assertExists().assertIsDisplayed()
            onNodeWithText(ON_ERROR_ALERT_DIALOG_MESSSAGE).assertExists()
            onNodeWithText(DISMISS_BUTTON).performClick()
            onAllNodesWithTag(ALERT_DIALOG).onFirst().assertDoesNotExist()


        }

    @Test
    fun testingElementExistWithLoadingState(): Unit =
        with(composeTestRule) {

            buildCountryListViewLandscape(
                networkStatus = ConnectionStatus.Lost,
                stateAction = StateAction.Loading
            )

            onNodeWithTag(COUNTRY_LIST_VIEW_LANDSCAPE).assertExists().assertIsDisplayed()
            onNodeWithTag(LOADING_ANIMATION).assertExists().assertIsDisplayed()
            onNodeWithTag(NETWORK_LABEL).assertExists().assertIsDisplayed().assertTextEquals(
                ASSERTING_CONNECTION_STATUS_LOST)
            onAllNodesWithTag(ALERT_DIALOG).onFirst().assertDoesNotExist()

        }


    private fun ComposeContentTestRule.buildCountryListViewLandscape(
        networkStatus: ConnectionStatus,
        stateAction: StateAction,
    ) {
        setContent {
            CountryListViewLandscape(
                networkStatus = networkStatus, state = stateAction, viewModel = viewModel
            )
        }
    }
}