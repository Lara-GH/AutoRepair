package org.autorepair.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.autorepair.presentation.splash.SplashEvent
import org.autorepair.presentation.splash.SplashScreenModel

object SplashScreen : Screen {

    @Composable
    override fun Content() {
        SplashContent()
    }
}

@Composable
fun Screen.SplashContent() {

    val screenModel = rememberScreenModel { SplashScreenModel() }
    val state by screenModel.state.collectAsState()

    val navigator = LocalNavigator.currentOrThrow

    Box(modifier = Modifier.fillMaxSize().background(color = Color.Blue))

    LaunchedEffect(true) {
        screenModel.events.collect {
            when (it) {
                SplashEvent.NavigateToLogin -> navigator.replace(LoginScreen)
                SplashEvent.NavigateToMain -> navigator.replace(MainScreen)
            }
        }
    }

    LaunchedEffect(true) {
        screenModel.checkCurrentUser()
    }
}