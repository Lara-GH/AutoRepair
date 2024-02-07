package org.autorepair.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.Typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
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
import org.autorepair.presentation.signup.SignUpEvent
import org.autorepair.presentation.signup.SignUpScreenModel

object SignUpScreen : Screen {
    @Composable
    override fun Content() {
        SignUpContent()
    }
}

@Composable
fun Screen.SignUpContent() {
    val screenModel = rememberScreenModel { SignUpScreenModel() }
    val state by screenModel.state.collectAsState()

    val navigator = LocalNavigator.currentOrThrow

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.background),
    ) {

        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(32.dp)
                .background(color = MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            SignUpHeader(modifier = Modifier.padding(bottom = 50.dp, top = 150.dp))
            SignUpForm(
                enabled = state.formEnabled,
                isLoading = state.isLoading,
                isIncorrectData = state.isIncorrectData,
                onEmailChange = screenModel::onEmailChanged,
                onPasswordChange = { screenModel.onPassChanged(it) },
                onSignUpClick = screenModel::onSignUpClick
            )
            SignUpFooter(
                onSignInClick = screenModel::onSignInClick
            )

            if (state.isIncorrectData) {
                Text("incorrectData")
            }

            if (state.error != null) {
                Text("error = ${state.error}")
            }
        }
    }

    LaunchedEffect(true) {
        screenModel.events.collect { event ->
            when (event) {
                is SignUpEvent.NavigateToHome -> navigator.replace(TabScreen)
                is SignUpEvent.NavigateToLogin -> navigator.pop()
            }
        }
    }
}

@Composable
fun SignUpForm(
    enabled: Boolean,
    isLoading: Boolean,
    isIncorrectData: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    val loginTypography = Typography(
        body1 = TextStyle(
            fontFamily = fontFamilyResource(MR.fonts.Montserrat.extraBold),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        ),
        body2 = TextStyle(
            fontFamily = fontFamilyResource(MR.fonts.Montserrat.extraBold),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.outline
        )
    )

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 50.dp),
        horizontalAlignment = Alignment.End
    ) {

        val focusedField = remember { mutableStateOf(FocusedField.NONE) }

        WrapCard(focusedField.value == FocusedField.EMAIL) {
            TextField(
                modifier = Modifier.fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.background)
                    .onFocusChanged {
                        if (it.isFocused) focusedField.value = FocusedField.EMAIL
                    },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "Localized description",
                        modifier = Modifier.size(22.dp),
                        tint = MaterialTheme.colorScheme.outline
                    )
                },
                enabled = enabled,
                value = email,
                onValueChange = { email = it },
                label = {
                    Text(
                        text = stringResource(MR.strings.email),
                        style = loginTypography.body2
                    )
                },
                singleLine = true,
                textStyle = loginTypography.body1,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary,
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        WrapCard(focusedField.value == FocusedField.PASSWORD) {
            TextField(
                modifier = Modifier.fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.background)
                    .onFocusChanged {
                        if (it.isFocused) focusedField.value = FocusedField.PASSWORD
                    },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Localized description",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.outline,
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                enabled = enabled,
                value = password,
                onValueChange = { password = it },
                label = {
                    Text(
                        text = stringResource(MR.strings.password),
                        style = loginTypography.body2
                    )
                },
                singleLine = true,
                textStyle = loginTypography.body1,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        WrapCard(focusedField.value == FocusedField.CONFIRMPASSWORD) {
            TextField(
                modifier = Modifier.fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.background)
                    .onFocusChanged {
                        if (it.isFocused) focusedField.value = FocusedField.CONFIRMPASSWORD
                    },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Localized description",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.outline,
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                enabled = enabled,
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = {
                    Text(
                        text = stringResource(MR.strings.confirm_password),
                        style = loginTypography.body2
                    )
                },
                singleLine = true,
                textStyle = loginTypography.body1,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )
        }
        Spacer(modifier = Modifier.height(40.dp))

        Button(
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            onClick = {
                onSignUpClick()
            }
        ) {
            Row(
                modifier = Modifier.padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(MR.strings.sign_up2),
                    color = MaterialTheme.colorScheme.background,
                    style = TextStyle(
                        fontFamily = fontFamilyResource(MR.fonts.Montserrat.semiBold),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp,
                    )
                )
                if (isLoading) {
                    CircularProgressIndicator()
                }
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.background
                )
            }
        }
    }
}

@Composable
fun SignUpHeader(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = fontFamilyResource(MR.fonts.Montserrat.extraBold),
            text = stringResource(MR.strings.create_account)
        )
    }
}


@Composable
fun SignUpFooter(onSignInClick: () -> Unit) {

    Row(
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
            .padding(bottom = 10.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        ClickableText(
            modifier = Modifier.padding(end = 5.dp),
            text = AnnotatedString(stringResource(MR.strings.already_have_an_account)),
            style = TextStyle(
                color = MaterialTheme.colorScheme.secondary, fontFamily = fontFamilyResource(
                    MR.fonts.Montserrat.bold
                )
            ),
            onClick = {
                onSignInClick()
            }
        )
        ClickableText(
            text = AnnotatedString(stringResource(MR.strings.sign_in)),
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary, fontFamily = fontFamilyResource(
                    MR.fonts.Montserrat.bold
                )
            ),
            onClick = {
                onSignInClick()
            }
        )
    }
}