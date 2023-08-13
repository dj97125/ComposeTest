package com.danielcaballero.composetest.ui.util

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import com.danielcaballero.composetest.ui.theme.Typography


@Composable
fun AlertError(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onRetry: () -> Unit,
    title: String,
    body: String
) {


    if (isVisible) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
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
                    onDismiss()
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
                TextButton(onClick = { onDismiss() }) {
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
fun AlertWelcome(isVisible: Boolean, onDismiss: () -> Unit, title: String, body: String) {

    if (isVisible) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
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
                    onDismiss()
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
