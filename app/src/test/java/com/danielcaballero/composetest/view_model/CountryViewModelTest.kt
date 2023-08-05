package com.danielcaballero.composetest.view_model



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
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.slot
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CountryViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    @MockK
    lateinit var getCountryUseCase: GetCountryUseCase

    @get:Rule
    val mockkRule = MockKRule(this)

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    lateinit var viewModel: CountryViewModel

    @Before
    fun setup() {

        viewModel = CountryViewModel(
            corroutineExceptionHandler = coroutineExceptionHandler,
            getCountryUseCase = getCountryUseCase
        )


    }

    @Test
    fun `test everything works`() = runTest {
        assertTrue(true)
    }

    @Test
    fun `at the beginning, country response is Success`() = runTest {
        coEvery { getCountryUseCase() } returns flowOf(
            StateAction.Succes(fakeCountry, "200")
        )

        val stateActionList = mutableListOf<StateAction?>()

        viewModel.getCountries()



        viewModel.countryResponse.test {

            stateActionList.add(awaitItem())
            val response = stateActionList.first() as StateAction.Succes<*>
            assertEquals(1, stateActionList.size)
            assertEquals("200", response.code)
//            assertEquals(fakeCountry, response.respoonse)
        }



    }
}