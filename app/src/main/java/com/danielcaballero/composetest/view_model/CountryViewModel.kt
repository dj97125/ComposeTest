package com.danielcaballero.composetest.view_model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielcaballero.composetest.common.ConnectionStatus
import com.danielcaballero.composetest.common.NetworkConnectivityObserver
import com.danielcaballero.composetest.common.StateAction
import com.danielcaballero.composetest.use_cases.GetCountryUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

data class VisibilityComponents(
    val component: AlertDialogs,
    var isVisible: Boolean,
)

enum class AlertDialogs {
    CountryResponse, Observer
}

class CountryViewModel(
    val getCountryUseCase: GetCountryUseCase,
    private val handler: CoroutineExceptionHandler,
) : ViewModel() {

    private val _countryResponse: MutableStateFlow<StateAction?> =
        MutableStateFlow(null)
    val countryResponse: StateFlow<StateAction?>
        get() = _countryResponse.asStateFlow()



    private val _network: MutableStateFlow<ConnectionStatus?> =
        MutableStateFlow(null)
    val network: StateFlow<ConnectionStatus?>
        get() = _network.asStateFlow()

    private val visibility = Channel<Boolean>()
    val visibilityFlow = visibility.receiveAsFlow()


    var isVisibleCountryResponseAlert by mutableStateOf(false)
        private set

    var isVisibleNetworkObserverAlert by mutableStateOf(false)
        private set


    var networkStatus by mutableStateOf<ConnectionStatus?>(null)
        private set


    init {
        getCountries()

    }




    fun getCountries() {
        viewModelScope.launch(handler) {
            _countryResponse.value = StateAction.Loading
            supervisorScope {
                launch {
                    getCountryUseCase().collect() { stateReceiveed ->
                        _countryResponse.update {
                            stateReceiveed
                        }
                        visibility.send(true)
                        isVisibleCountryResponseAlert = true
                    }
                }
            }
        }
    }

}