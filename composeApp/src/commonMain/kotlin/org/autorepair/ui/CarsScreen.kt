package org.autorepair.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

object CarsScreen : Screen {

    @Composable
    override fun Content() {
        CarsContent()
    }
}

@Composable
fun Screen.CarsContent() {
//    val screenModel = getScreenModel<SplashScreenModel>()
//    val state by screenModel.state.collectAsState()

    val navigator = LocalNavigator.currentOrThrow

    Column(
        Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("CarsScreen!")
    }

}