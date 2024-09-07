package com.application.optimization.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.application.optimization.utils.DialogState

@Composable
fun BaseScreen(
    baseScreenState: BaseScreenState<*>, content: @Composable () -> Unit
) {
    AppBase(
        loading = baseScreenState.loading, dialog = baseScreenState.dialog, content = content
    )
}

@Composable
private fun AppBase(
    loading: Boolean = false,
    dialog: DialogState? = null,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        content()

        if (loading) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        dialog?.let {

            Dialog(onDismissRequest = { }) {
                Surface(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 24.dp)
                        .wrapContentHeight(), shape = MaterialTheme.shapes.medium
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = dialog.title,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(top = 16.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = dialog.message,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 16.dp),
                            textAlign = TextAlign.Center
                        )
                        dialog.firstButton?.let { buttonText ->
                            Button(onClick = buttonText.onButtonClicked) {
                                Text(
                                    text = buttonText.text,
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                        dialog.secondButton?.let { buttonText ->
                            TextButton(onClick = buttonText.onButtonClicked) {
                                Text(
                                    text = buttonText.text,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}