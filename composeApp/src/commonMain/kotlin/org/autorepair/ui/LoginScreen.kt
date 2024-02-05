package org.autorepair.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.autorepair.presentation.LoginScreenModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

object LoginScreen : Screen {

    @Composable
    override fun Content() {
        LoginScreenContent()
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LoginScreenContent(
) {

    val screenModel = remember { LoginScreenModel() }
    val state by screenModel.state.collectAsState()

    var greetingText by remember { mutableStateOf("Hello World!") }
    var showImage by remember { mutableStateOf(false) }

    val navigator = LocalNavigator.currentOrThrow

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//        LoginForm({})
        Button(onClick = {
//            navigator.push(MainScreen)
            screenModel.onLoginClick()
        }) {
            Text(greetingText)
        }

        if(state.isLoading) {
            CircularProgressIndicator()
        }
        AnimatedVisibility(showImage) {
            Image(
                painterResource("compose-multiplatform.xml"),
                null
            )
        }
    }
}

@Composable
fun LoginForm(
    enabled: Boolean,
    onTextChange: (String, String) -> Unit
) {

}