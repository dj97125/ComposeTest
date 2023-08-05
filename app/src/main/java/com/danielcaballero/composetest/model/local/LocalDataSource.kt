package com.danielcaballero.composetest.model.local

import com.danielcaballero.composetest.common.Code400Exception
import com.danielcaballero.composetest.common.NullBody
import com.danielcaballero.composetest.common.StateAction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


interface LocalDataSource {
    fun getCountriesDataSource(): Flow<StateAction>
}

class LocalDataSourceImpl @Inject constructor(
    private val service: NetworkCountry

) : LocalDataSource {
    override fun getCountriesDataSource(): Flow<StateAction> = flow {
        val networkService = service.getAllCountries()

        networkService.body()?.let { response ->
            emit(StateAction.Succes(response, networkService.code().toString()))
        } ?: run {
            emit(StateAction.Errror(NullBody()))
        }

        if (!networkService.isSuccessful) {
            if (networkService.code().toString().startsWith("4")) {
                emit(StateAction.Errror(Code400Exception()))
            }
        }

    }
}