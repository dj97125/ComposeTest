package com.danielcaballero.composetest.model.network.country_response

import kotlinx.serialization.Serializable

@Serializable
data class CountryResponse(
    val capital: String?,
    val code: String?,
    val flag: String?,
    val name: String?,
    val region: String?
)
