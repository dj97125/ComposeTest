package com.danielcaballero.composetest.use_cases

import com.danielcaballero.composetest.domain.Repository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetCountryUseCase : KoinComponent {
    private val repository: Repository by inject()

    operator fun invoke() =
        repository.getCountriesDomainLayer()
}