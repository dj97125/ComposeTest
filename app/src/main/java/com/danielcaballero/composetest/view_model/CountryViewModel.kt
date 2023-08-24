package com.danielcaballero.composetest.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielcaballero.composetest.common.ConnectionStatus
import com.danielcaballero.composetest.common.StateAction
import com.danielcaballero.composetest.use_cases.GetCountryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

data class VisibilityComponents(
    val component: AlertDialogs,
    var isVisible: Boolean,
)

enum class AlertDialogs {
    CountryResponse, Observer
}

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val getCountryUseCase: GetCountryUseCase,
    private val corroutineExceptionHandler: CoroutineExceptionHandler
) : ViewModel() {

    private val _countryResponse: MutableStateFlow<StateAction?> =
        MutableStateFlow(null)
    val countryResponse: StateFlow<StateAction?>
        get() = _countryResponse.asStateFlow()


    private val _isFirstVisit = Channel<Boolean>()
    val isFirstVisit = _isFirstVisit.receiveAsFlow()


    var isVisibleCountryResponseAlert by mutableStateOf(false)
        private set

    var isVisibleNetworkObserverAlert by mutableStateOf(false)
        private set


    var networkStatus by mutableStateOf<ConnectionStatus?>(null)
        private set

    init {
        getCountries()
    }

    fun changeVisibility(visibilityComponent: VisibilityComponents) {

        when (visibilityComponent.component) {
            AlertDialogs.Observer -> isVisibleNetworkObserverAlert = visibilityComponent.isVisible

            AlertDialogs.CountryResponse -> isVisibleCountryResponseAlert =
                visibilityComponent.isVisible

        }

    }


    fun networkStatus(status: ConnectionStatus) {
        networkStatus = status
    }

    fun getCountries() {
        _countryResponse.value = StateAction.Loading
        viewModelScope.launch(corroutineExceptionHandler) {
            supervisorScope {
                launch {
                    getCountryUseCase().collect() {
                        _countryResponse.value = it
                        isVisibleCountryResponseAlert = true
                        _isFirstVisit.send(false)
                    }
                }
            }
        }
    }

}