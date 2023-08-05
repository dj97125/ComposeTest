package com.danielcaballero.composetest.model.local

import com.danielcaballero.composetest.common.END_POINT
import com.danielcaballero.composetest.model.local.country_response.CountryResponse
import retrofit2.Response
import retrofit2.http.GET

interface NetworkCountry {

    @GET(END_POINT)
    suspend fun getAllCountries(): Response<List<CountryResponse>>

}