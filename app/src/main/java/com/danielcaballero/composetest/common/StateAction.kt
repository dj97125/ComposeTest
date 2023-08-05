package com.danielcaballero.composetest.common

import java.lang.Exception

sealed interface StateAction {
    class Succes<T>(val respoonse: T, val code: String): StateAction
    class Errror(val exception: Exception): StateAction
    object Loading : StateAction
}


