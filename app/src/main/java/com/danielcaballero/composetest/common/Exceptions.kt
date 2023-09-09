package com.danielcaballero.composetest.common

class Code400Exception(
    message: String = "Invalid Parameters"
) : Exception(message)

class NullBody(
    message: String = "Null Body"
) : Exception(message)


class NoInternetConnection(
    message: String = "No Internet Conection"
) : Exception(message)

class EmptyCacheException(
    message: String = "Empty Data Base"
) : Exception(message)