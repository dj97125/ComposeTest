package com.danielcaballero.composetest.use_cases

import com.danielcaballero.composetest.domain.Repository
import javax.inject.Inject

class GetCountryUseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke(isConnected: Boolean) =
        repository.getCountriesDomainLayer(isConnected = isConnected)
}