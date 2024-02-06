package org.autorepair.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.stringResource
import org.autorepair.MR
import org.autorepair.presentation.login.LoginEvent
import org.autorepair.presentation.login.LoginScreenModel
import org.jetbrains.compose.resources.ExperimentalResourceApi

object LoginScreen : Screen {

    @Composable
    override fun Content() {
        LoginScreenContent()
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Screen.LoginScreenContent(
) {
    val screenModel = rememberScreenModel { LoginScreenModel() }
    val state by screenModel.state.collectAsState()

    val navigator = LocalNavigator.currentOrThrow

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoginHeader(modifier = Modifier.padding(bottom = 16.dp))
        LoginForm(
            enabled = state.formEnabled,
            isLoading = state.isLoading,
            isIncorrectData = state.isIncorrectData,
            onEmailChange = screenModel::onEmailChanged,
            onPasswordChange = { screenModel.onPassChanged(it) },
            onLoginClick = screenModel::onLoginClick,
            email = state.email,
            pass = state.pass
        )
        LoginFooter()

        if(state.isIncorrectData) {
            Text("incorrectData")
        }

        if(state.error != null) {
            Text("error = ${state.error}")
        }
    }

    LaunchedEffect(true) {
        screenModel.events.collect { event ->
            when(event) {
                is LoginEvent.NavigateToMain -> navigator.replace(MainScreen)
            }
        }
    }
}

enum class FocusedField {
    EMAIL,
    PASSWORD,
    NONE
}

@Composable
fun LoginForm(
    email: String,
    pass: String,
    enabled: Boolean,
    isLoading: Boolean,
    isIncorrectData: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 50.dp),
        horizontalAlignment = Alignment.End
    ) {

        val focusedField = remember { mutableStateOf(FocusedField.NONE) }

        WrapCard(focusedField.value == FocusedField.EMAIL) {
            TextField(
                modifier = Modifier.fillMaxWidth().background(Color.Transparent)
                    .onFocusChanged { focusedField.value = FocusedField.EMAIL },
                enabled = enabled,
                colors = TextFieldDefaults.textFieldColors(),
                value = email,
                onValueChange = onEmailChange,

                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Localized description",
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        WrapCard(focusedField.value == FocusedField.PASSWORD) {
            TextField(
                modifier = Modifier.fillMaxWidth().background(Color.Transparent)
//                    .onFocusChanged { focusedField.value = FocusedField.PASSWORD },
                ,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Localized description",
                        modifier = Modifier.size(24.dp)
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                enabled = enabled,
                value = pass,
                onValueChange = onPasswordChange
            )
        }

        Button(modifier = Modifier, onClick = {
            onLoginClick()
        }) {
            Text("Login")
            if (isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun WrapCard(shouldWrap: Boolean, content: @Composable () -> Unit) {
    if(shouldWrap) {
        Card(elevation = 10.dp) { content() }
    } else {
        content()
    }
}

@Composable
fun LoginHeader(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onBackground,
            text = stringResource(MR.strings.login_header_title)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier,
            fontSize = 16.sp,
            color = Color.Gray,
            fontFamily = fontFamilyResource(MR.fonts.Roboto.black),
            text = stringResource(MR.strings.login_header_subtitle)
        )
    }
}

@Composable
fun LoginFooter() {
}