package org.autorepair.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.autorepair.presentation.chat.ChatEvent
import org.autorepair.presentation.chat.ChatScreenModel
import org.autorepair.ui.chat.Messages

@Composable
fun Screen.ChatContent() {
    val screenModel = getScreenModel<ChatScreenModel>()
    val state by screenModel.state.collectAsState()
    val parentNavigator = LocalNavigator.currentOrThrow.parent ?: error("No parent navigator")

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        screenModel.events.collect {
            when (it) {
                ChatEvent.NavigateToLogin -> {
                    parentNavigator.popUntilRoot()
                    parentNavigator.replace(LoginScreen)
                }

                is ChatEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(it.text)
                }
            }
        }
    }

    Box(
        Modifier.fillMaxSize()
    ) {
        Column(
            Modifier.fillMaxWidth().fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(15.dp))
            Messages(state.messages)
        }
        SendMessageButton(onClick = { screenModel.onSendMessageClick() })

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Snackbar {
                Text(
                    modifier = Modifier.fillMaxWidth()
                        .align(Alignment.Center),
                    text = it.visuals.message,
                    color = Color.White,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun SendMessageButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        androidx.compose.material3.Button(
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            onClick = { onClick() },
            shape = RoundedCornerShape(5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                androidx.compose.material.Text(
                    text = "send message",
                    color = MaterialTheme.colorScheme.background,
                    style = TextStyle(
                        fontSize = 14.sp,
                    )
                )
            }
        }
    }
}
