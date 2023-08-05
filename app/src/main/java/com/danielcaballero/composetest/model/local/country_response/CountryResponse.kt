package com.danielcaballero.composetest.model.local.country_response

data class CountryResponse(
    val capital: String?,
    val code: String?,
    val currency: CurrencyResponse?,
    val flag: String?,
    val languaje: LanguajeResponse?,
    val name: String?,
    val region: String?
)

data class LanguajeResponse(
    val code: String?,
    val name: String?
)

data class CurrencyResponse(
    val code: String?,
    val name: String?,
    val symbol: String?
)
