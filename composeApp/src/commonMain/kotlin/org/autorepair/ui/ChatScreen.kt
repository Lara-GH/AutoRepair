package org.autorepair.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch
import org.autorepair.MR
import org.autorepair.presentation.chat.ChatEvent
import org.autorepair.presentation.chat.ChatScreenModel
import org.autorepair.ui.chat.Messages

object ChatScreen : Screen {
    @Composable
    override fun Content() {
        ChatContent()
    }
}

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

        MessageTextField(
            modifier = Modifier,
            value = state.message,
            onValueChange = screenModel::onMessageChanged,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            onClick = { screenModel.onSendMessageClick() },
        )

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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MessageTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    colors: TextFieldColors,
    onClick: () -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background)
//            .onFocusChanged { focused ->
//            if (!focused.isFocused) {
//                // Скрыть клавиатуру, когда текстовое поле теряет фокус
//                coroutineScope.launch {
//                    keyboardController?.hide()
//                }
//            }
//        }
    ,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.outline,
                )
            },
            trailingIcon = {
                if (value.isNotEmpty()) {
                    IconButton(
                        onClick = { onClick() }
                    ) {
                        Icon(
                            painter = painterResource(MR.images.arrow),
                            contentDescription = "Localized description",
                            modifier = Modifier.size(27.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            },
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = stringResource(MR.strings.message),
                    color = MaterialTheme.colorScheme.outline,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 18.sp,
                        letterSpacing = 0.sp,
                    )
                )
            },
            singleLine = true,
            colors = colors,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 18.sp,
                letterSpacing = 0.sp,
            )
        )
    }
}
