package com.danielcaballero.composetest.domain

import com.danielcaballero.composetest.common.StateAction
import com.danielcaballero.composetest.model.network.LocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


interface Repository {
    fun getCountriesDomainLayer(): Flow<StateAction>
}
class RepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
): Repository {


    override fun getCountriesDomainLayer(): Flow<StateAction> = flow {

            localDataSource.getCountriesDataSource().collect() { state ->
                when (state) {
                    is StateAction.Succes<*> -> {
                        val retrievedCountries = state.respoonse
                        val code = state.code

                        emit(StateAction.Succes(retrievedCountries, code))

                    }

                    is StateAction.Errror -> {
                        emit(StateAction.Errror(state.exception))
                    }

                    else -> {}
                }

            }

    }
}


