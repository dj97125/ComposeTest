package com.danielcaballero.composetest.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielcaballero.composetest.common.StateAction
import com.danielcaballero.composetest.domain.Repository
import com.danielcaballero.composetest.use_cases.GetCountryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject


@HiltViewModel
class CountryViewModel @Inject constructor(
    private val getCountryUseCase: GetCountryUseCase,
    private val corroutineExceptionHandler: CoroutineExceptionHandler
) : ViewModel() {

    private val _countryResponse: MutableStateFlow<StateAction?> =
        MutableStateFlow(null)
    val countryResponse: StateFlow<StateAction?>
        get() = _countryResponse.asStateFlow()

    init {
        getCountries()
    }

    fun getCountries() {
        _countryResponse.value = StateAction.Loading
        viewModelScope.launch(corroutineExceptionHandler) {
            supervisorScope {
                launch {
                    getCountryUseCase().collect() {
                        _countryResponse.value = it
                    }
                }
            }
        }
    }

}