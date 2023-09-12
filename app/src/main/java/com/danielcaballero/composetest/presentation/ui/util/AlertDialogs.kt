package com.danielcaballero.composetest.presentation.ui.util

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import com.danielcaballero.composetest.presentation.ui.theme.Typography




@Composable
fun AlertDialog(
    title: String,
    body: String,
    modifier: Modifier,
    onRetry: () -> Unit = {}
) {
    var visibility by remember { mutableStateOf(true) }

    if (visibility) {
        AlertDialog(
            modifier = modifier ,
            onDismissRequest = {
                visibility = false
            },
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
                    textAlign = TextAlign.Start,
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                    visibility = false
                    onRetry()
                }) {
                    AutoResizedText(
                        text = "Dismiss",
                        color = MaterialTheme.colorScheme.secondary,
                        style = Typography.titleMedium,
                        textAlign = TextAlign.Start,
                    )
                }
            })

    }



}


