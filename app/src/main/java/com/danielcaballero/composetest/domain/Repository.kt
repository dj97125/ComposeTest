package com.danielcaballero.composetest.domain

import com.danielcaballero.composetest.common.NetworkConnectivityObserver
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
    fun getCountriesDomainLayer(isConnected: Boolean): Flow<StateAction>
}
class RepositoryImpl @Inject constructor(
    private val networkConnectivityObserver: NetworkConnectivityObserver,
    private val localDataSource: LocalDataSource,
    private val networkDataSource: NetworkDataSource
): Repository {
    override fun getCountriesDomainLayer(isConnected: Boolean): Flow<StateAction> = flow {

        if (checkAvailability()) {
            networkDataSource.getCountriesDataSource().collect() { state ->
                when (state) {
                    is StateAction.Succes<*> -> {
                        val retrievedCountries = state.respoonse as List<CountryDomain>
                        val code = state.code

                        emit(StateAction.Succes(retrievedCountries, code))
                        localDataSource.insertCountries(retrievedCountries)

                    }

                    is StateAction.Errror -> {
                        emit(StateAction.Errror(state.exception))
                    }

                    else -> {}
                }
            }

        } else {
            val list = localDataSource.getAllCountries()
            emit(StateAction.Succes(list, "200"))

        }

    }

    private suspend fun checkAvailability(): Boolean = withContext(Dispatchers.IO) {
        return@withContext try {
            Socket().apply {
                connect(InetSocketAddress("8.8.8.8", 53), 1500)
                close()
            }
            true
        } catch (e: Exception) {
            false
        }

//        fun isConnected(): Boolean {
//            return try {
//                val sock = Socket()
//                sock.connect(InetSocketAddress("8.8.8.8", 53), 1500)
//                sock.close()
//                true
//            } catch (e: IOException) {
//                false
//            }
//        }
    }

}




