package com.danielcaballero.composetest.model.remote

import android.util.Log
import com.danielcaballero.composetest.CountryDataBase
import com.danielcaballero.composetest.domain.CountryDomain
import countries.database.CountryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import javax.inject.Inject

interface LocalDataSource {
    suspend fun getAllCountries(): List<CountryDomain>
    suspend fun insertCountries(countries: List<CountryDomain>)

}

class LocalDataSourceImpl @Inject constructor(
    countryDataBase: CountryDataBase
) : LocalDataSource {

    private val queries = countryDataBase.countryEntityQueries


    override suspend fun getAllCountries(): List<CountryDomain>  {
        return queries.getAllCountries().executeAsList().map { countryEntity ->
            supervisorScope {
                async { countryEntity.toCountryDomain() }
            }
        }.map { it.await() }

    }

    override suspend fun insertCountries(countries: List<CountryDomain>) {
        withContext(Dispatchers.IO) {
            countries.forEach { country ->

                queries.insertCountry(
                    capital = country.capital.toString(),
                    code = country.code.toString(),
                    flag = country.flag.toString(),
                    name = country.name.toString(),
                    region = country.region.toString(),
                    createdAt = Clock.System.now().toEpochMilliseconds()
                )
            }

        }
    }

}


private fun CountryEntity.toCountryDomain(): CountryDomain = CountryDomain(
    capital = this.capital,
    code = this.code,
    name = this.name,
    region = this.region,
    flag = ""
)
