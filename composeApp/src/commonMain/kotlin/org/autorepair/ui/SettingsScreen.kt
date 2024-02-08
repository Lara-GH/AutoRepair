package org.autorepair.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.stringResource
import org.autorepair.MR
import org.autorepair.presentation.settings.SettingsEvent
import org.autorepair.presentation.settings.SettingsScreenModel

object SettingsScreen : Screen {
    @Composable
    override fun Content() {
        SettingsContent()
    }
}

@Composable
fun Screen.SettingsContent() {

    val screenModel = getScreenModel<SettingsScreenModel>()
    val state by screenModel.state.collectAsState()

    val parentNavigator = LocalNavigator.currentOrThrow.parent ?: error("No parent navigator")

    Column(
        Modifier.fillMaxWidth().fillMaxHeight().padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        LogOutButton(onClick = screenModel::onLogoutClick)
    }

    LaunchedEffect(true) {
        screenModel.events.collect { event ->
            when (event) {
                is SettingsEvent.NavigateToLogin -> parentNavigator.replaceAll(LoginScreen)
            }
        }
    }
}

@Composable
fun LogOutButton(onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(4.dp))
            .border(
                BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
                RoundedCornerShape(4.dp)
            )
            .clickable { onClick() }
    ) {
        Text(
            text = stringResource(MR.strings.log_out),
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 15.dp),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}