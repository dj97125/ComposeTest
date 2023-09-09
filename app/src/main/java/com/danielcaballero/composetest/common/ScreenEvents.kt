package com.danielcaballero.composetest.common

sealed interface ScreenEvents {
    class showAlertDialog(val message: String): StateAction
}