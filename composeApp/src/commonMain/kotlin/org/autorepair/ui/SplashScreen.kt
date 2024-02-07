package org.autorepair.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
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

    val screenModel = getScreenModel<SplashScreenModel>()
    val state by screenModel.state.collectAsState()

    val navigator = LocalNavigator.currentOrThrow

    Box(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.primary))

    LaunchedEffect(true) {
        screenModel.events.collect {
            when (it) {
                SplashEvent.NavigateToLogin -> navigator.replace(LoginScreen)
                SplashEvent.NavigateToHome -> navigator.replace(HomeScreen)
            }
        }
    }

    LaunchedEffect(true) {
        screenModel.checkCurrentUser()
    }
}