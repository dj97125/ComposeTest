package com.danielcaballero.composetest.view_model



import androidx.test.espresso.matcher.ViewMatchers.assertThat
import app.cash.turbine.test
import com.danielcaballero.composetest.common.StateAction
import com.danielcaballero.composetest.comon.CoroutinesTestRule
import com.danielcaballero.composetest.comon.fakeCountry
import com.danielcaballero.composetest.domain.Repository
import com.danielcaballero.composetest.use_cases.GetCountryUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import com.nhaarman.mockitokotlin2.verify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.slot
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import androidx.lifecycle.Observer
import com.danielcaballero.composetest.common.EmptyCacheException
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class CountryViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    @MockK
    lateinit var getCountryUseCase: GetCountryUseCase

    @get:Rule
    val mockkRule = MockKRule(this)

    private val exceptions = mutableListOf<Throwable>()

    private val visibilityObserver = mock<Observer<Boolean>>()

    val handler = CoroutineExceptionHandler { _, throwable ->
        exceptions.add(throwable)
    }



    lateinit var viewModel: CountryViewModel

    @Before
    fun setup() {
        viewModel = CountryViewModel(
            handler = handler,
            getCountryUseCase = getCountryUseCase
        )


    }

    @Test
    fun `test everything works`() = runTest {
        assertTrue(true)
    }

    @Test
    fun `at the beginning, country response is Loading`() = runTest {
        val stateActionList = mutableListOf<StateAction?>()

        viewModel.getCountries()
        runCurrent()

        viewModel.countryResponse.test {
            stateActionList.add(awaitItem())
            assertThat(stateActionList.first()).isInstanceOf(StateAction.Loading::class.java)

        }
    }


    @Test
    fun `when start, country response is Success`() = runTest {
        coEvery { getCountryUseCase() } returns flowOf(
            StateAction.Succes(fakeCountry, "200")
        )

        val stateActionList = mutableListOf<StateAction?>()
        val channelVisibility = mutableListOf<Boolean>()

        viewModel.getCountries()
        runCurrent()

        viewModel.countryResponse.test {

            stateActionList.add(awaitItem())

            val response = stateActionList.first() as StateAction.Succes<*>
            assertThat(stateActionList.first()).isInstanceOf(StateAction.Succes::class.java)
            assertEquals("200", response.code)
            assertEquals(fakeCountry, response.respoonse)
            cancel()
        }

        viewModel.visibilityFlow.test {
            channelVisibility.add(expectMostRecentItem())
            val result = channelVisibility.first()
            assertEquals(true,result)
            cancel()
        }

        coVerify {
            getCountryUseCase()
        }
    }

    @Test
    fun `when start, country response is Error`() = runTest {
        coEvery { getCountryUseCase() } returns flowOf(
            StateAction.Errror(EmptyCacheException())
        )

        val stateActionList = mutableListOf<StateAction?>()
        val channelVisibility = mutableListOf<Boolean>()

        viewModel.getCountries()
        runCurrent()

        viewModel.countryResponse.test {

            stateActionList.add(awaitItem())

            val response = stateActionList.first() as StateAction.Errror
            assertThat(stateActionList.first()).isInstanceOf(StateAction.Errror::class.java)
            assertEquals(EmptyCacheException().message, response.exception.message)
            cancel()
        }

        viewModel.visibilityFlow.test {
            channelVisibility.add(expectMostRecentItem())
            val result = channelVisibility.first()
            assertEquals(true,result)
            cancel()
        }

        coVerify {
            getCountryUseCase()
        }
    }



}