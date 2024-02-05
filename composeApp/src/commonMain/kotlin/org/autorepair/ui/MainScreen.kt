package org.autorepair.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

object MainScreen: Screen {
    @Composable
    override fun Content() {
        MainScreenContent()
    }
}

@Composable
fun MainScreenContent() {
    val navigator = LocalNavigator.currentOrThrow

    Column(modifier = Modifier.fillMaxWidth()) {
        Text("main screen")
        Button(onClick = {
            navigator.pop()
        }) {
            Text("to login")
        }
    }
}