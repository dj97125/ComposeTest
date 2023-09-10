package com.danielcaballero.composetest.domain

import com.danielcaballero.composetest.common.EmptyCacheException
import com.danielcaballero.composetest.common.StateAction
import com.danielcaballero.composetest.model.network.NetworkDataSource
import com.danielcaballero.composetest.model.remote.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retry
import java.net.InetSocketAddress
import java.net.Socket


interface Repository {
    fun getCountriesDomainLayer(): Flow<StateAction>
}

class RepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val networkDataSource: NetworkDataSource
) : Repository {
    override fun getCountriesDomainLayer(): Flow<StateAction> = flow {
        checkAvailabilityFlow().retry(3).collect() { networkState ->
            if (networkState) {
                networkDataSource.getCountriesDataSource().collect() { state ->
                    when (state) {
                        is StateAction.Succes<*> -> {
                            val retrievedCountries = state.respoonse as List<CountryDomain>

                            localDataSource.insertCountries(retrievedCountries)


                        }

                        is StateAction.Errror -> {
                            emit(StateAction.Errror(state.exception))
                        }

                        else -> {}
                    }
                }
            }
        }

        val list = localDataSource.getAllCountries()

        if (list.isNotEmpty()) {
            emit(StateAction.Succes(list, "200"))
        } else {
            emit(StateAction.Errror(EmptyCacheException()))
        }

    }.flowOn(Dispatchers.IO)

    private fun checkAvailabilityFlow(): Flow<Boolean> = flow {
        Socket().apply {
            connect(InetSocketAddress("8.8.8.8", 53), 1500)
            close()
        }
        emit(true)
    }.catch {
        emit(false)
    }.flowOn(Dispatchers.IO)

}







