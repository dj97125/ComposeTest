package com.danielcaballero.composetest.model.network

import com.danielcaballero.composetest.common.Code400Exception
import com.danielcaballero.composetest.common.NullBody
import com.danielcaballero.composetest.common.StateAction
import com.danielcaballero.composetest.domain.CountryDomain
import com.danielcaballero.composetest.model.network.country_response.CountryResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


interface NetworkDataSource {
    fun getCountriesDataSource(): Flow<StateAction>
}

class NetworkDataSourceImpl @Inject constructor(
    private val service: NetworkCountry

) : NetworkDataSource {
    override fun getCountriesDataSource(): Flow<StateAction> = flow {
        val networkService = service.getAllCountries()

        networkService.body()?.let { response ->
            emit(StateAction.Succes(response.map {
                it.toCountryDomain()
            }, networkService.code().toString()))


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


private fun List<CountryResponse>.toCountryDomain(): List<CountryDomain> = map {
    it.toCountryDomain()
}

private fun CountryResponse.toCountryDomain(): CountryDomain = CountryDomain(
    capital = capital,
    code = code,
    name = name,
    region = region,
    flag = ""
)