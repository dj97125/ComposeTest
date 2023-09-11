package com.danielcaballero.composetest.common

import com.danielcaballero.composetest.domain.CountryDomain

val fakeCountry =
    CountryDomain(
        capital = "Morelia",
        code = null,
        name = null,
        region = "Michoacan",
        flag = null
    )

val fakeCountryList = listOf<CountryDomain>(
    CountryDomain(
        capital = "Morelia",
        code = null,
        name = null,
        region = "Michoacan",
        flag = null
    ),
    CountryDomain(
        capital = "Phoenix",
        code = null,
        name = null,
        region = "USA",
        flag = null
    ),
    CountryDomain(
        capital = "Georgia",
        code = null,
        name = null,
        region = "USA",
        flag = null
    ),
)