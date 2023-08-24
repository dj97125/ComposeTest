package com.danielcaballero.composetest.model.network

import com.danielcaballero.composetest.common.END_POINT_COUNTRIES_KTOR
import com.danielcaballero.composetest.common.StateAction
import com.danielcaballero.composetest.domain.CountryDomain
import com.danielcaballero.composetest.model.network.country_response.CountryResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import javax.inject.Inject


interface NetworkDataSource {
    fun getCountriesDataSource(): Flow<StateAction>
}

class NetworkDataSourceImpl @Inject constructor(
    private val service: HttpClient

) : NetworkDataSource {
    override fun getCountriesDataSource(): Flow<StateAction> = flow {

        try {
            val response: List<CountryResponse> = service.get {
                url(END_POINT_COUNTRIES_KTOR)
            }
            emit(StateAction.Succes(response.toCountryDomain(), "Data From Network"))
        } catch (e: NoTransformationFoundException) {
            val responseString: String = service.get(END_POINT_COUNTRIES_KTOR)

            val json = kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
            }
            val response = json.decodeFromString<List<CountryResponse>>(responseString)
            emit(StateAction.Succes(response.toCountryDomain(), "Data From Network"))
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