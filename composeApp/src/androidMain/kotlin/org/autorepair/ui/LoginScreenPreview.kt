package org.autorepair.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun LoginPreview() {
    MaterialTheme {
        LoginScreen.Content()
    }
}