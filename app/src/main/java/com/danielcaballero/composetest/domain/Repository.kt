package com.danielcaballero.composetest.domain

import com.danielcaballero.composetest.common.StateAction
import com.danielcaballero.composetest.model.network.NetworkDataSource
import com.danielcaballero.composetest.model.remote.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.net.InetSocketAddress
import java.net.Socket
import javax.inject.Inject


interface Repository {
    fun getCountriesDomainLayer(): Flow<StateAction>
}
class RepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val networkDataSource: NetworkDataSource
): Repository {
    override fun getCountriesDomainLayer(): Flow<StateAction> = flow {

        val list = localDataSource.getAllCountries()

        if (list.isEmpty().not()) {
            emit(StateAction.Succes(list, "200"))
        } else {
            networkDataSource.getCountriesDataSource().collect() { state ->
                when (state) {
                    is StateAction.Succes<*> -> {
                        val retrievedCountries = state.respoonse as List<CountryDomain>
                        val code = state.code
                        localDataSource.insertCountries(retrievedCountries)
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

}
//
//    private suspend fun checkAvailability(): Boolean = withContext(Dispatchers.IO) {
//        return@withContext try {
//            Socket().apply {
//                connect(InetSocketAddress("8.8.8.8", 53), 1500)
//                close()
//            }
//            true
//        } catch (e: Exception) {
//            false
//        }
//    }






