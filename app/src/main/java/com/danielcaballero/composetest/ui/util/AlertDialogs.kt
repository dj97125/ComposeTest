package com.danielcaballero.composetest.ui.util

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import com.danielcaballero.composetest.ui.theme.Typography


@Composable
fun AlertError(onRetry: () -> Unit, title: String, body: String) {

    var openDialog by remember { mutableStateOf(true) }

    if (openDialog) {
        AlertDialog(
            onDismissRequest = { openDialog = false },
            title = {
                AutoResizedText(
                    text = title,
                    color = MaterialTheme.colorScheme.secondary,
                    style = Typography.titleMedium,
                    textAlign = TextAlign.Start
                )
            },
            text = {
                AutoResizedText(
                    text = body,
                    color = MaterialTheme.colorScheme.secondary,
                    style = Typography.titleMedium,
                    textAlign = TextAlign.Start
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    onRetry()
                    openDialog = false
                }) {
                    AutoResizedText(
                        text = "Retry",
                        color = MaterialTheme.colorScheme.secondary,
                        style = Typography.titleMedium,
                        textAlign = TextAlign.Start
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { openDialog = false }) {
                    AutoResizedText(
                        text = "Dismiss",
                        color = MaterialTheme.colorScheme.secondary,
                        style = Typography.titleMedium,
                        textAlign = TextAlign.Start
                    )
                }
            }
        )
    }


}


@Composable
fun AlertWelcome(title: String, body: String) {

    var openDialog by remember { mutableStateOf(true) }

    if (openDialog) {
        AlertDialog(
            onDismissRequest = { openDialog = false },
            title = {
                AutoResizedText(
                    text = title,
                    color = MaterialTheme.colorScheme.secondary,
                    style = Typography.titleMedium,
                    textAlign = TextAlign.Start
                )
            },
            text = {
                AutoResizedText(
                    text = body,
                    color = MaterialTheme.colorScheme.secondary,
                    style = Typography.titleMedium,
                    textAlign = TextAlign.Start
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    openDialog = false
                }) {
                    AutoResizedText(
                        text = "Dismiss",
                        color = MaterialTheme.colorScheme.secondary,
                        style = Typography.titleMedium,
                        textAlign = TextAlign.Start
                    )
                }
            }
        )
    }


}
