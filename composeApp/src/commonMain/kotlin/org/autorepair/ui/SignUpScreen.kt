package org.autorepair.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.stringResource
import org.autorepair.MR
import org.autorepair.presentation.signup.SignUpEvent
import org.autorepair.presentation.signup.SignUpScreenModel
import org.autorepair.ui.features.SnackbarComponent
import org.autorepair.ui.manager.ManagerTabScreen
import org.autorepair.ui.mechanic.MechanicTabScreen
import org.autorepair.ui.theme.CustomTypography
import kotlin.reflect.KFunction1

object SignUpScreen : Screen {
    @Composable
    override fun Content() {
        SignUpContent()
    }
}

@Composable
fun Screen.SignUpContent() {
    val screenModel = getScreenModel<SignUpScreenModel>()
    val state by screenModel.state.collectAsState()

    val navigator = LocalNavigator.currentOrThrow
    val snackbarHostState = remember { SnackbarHostState() }

    Box(Modifier.fillMaxSize()) {

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
                    email = state.email,
                    password = state.password,
                    confirmPassword = state.confirmPassword,
                    isIncorrectData = state.isIncorrectData,
                    onEmailChange = screenModel::onEmailChanged,
                    onPasswordChange = screenModel::onPasswordChanged,
                    onConfirmPasswordChange = screenModel::onConfirmPasswordChanged,
                    onSignUpClick = screenModel::onSignUpClick
                )
                SignUpFooter(
                    onSignInClick = screenModel::onSignInClick
                )

                if (state.isIncorrectData) {
                    Text("incorrect data")
                }

                if (state.isUserExists) {
                    Text("User already exists")
                }

                if (state.error != null) {
                    Text("error = ${state.error}")
                }
            }
        }

        LaunchedEffect(true) {
            screenModel.events.collect { event ->
                when (event) {
                    is SignUpEvent.NavigateToUserHome -> navigator.replace(UserTabScreen)
                    is SignUpEvent.NavigateToMechanicHome -> navigator.replace(MechanicTabScreen)
                    is SignUpEvent.NavigateToManagerHome -> navigator.replace(ManagerTabScreen)
                    is SignUpEvent.NavigateToLogin -> navigator.pop()
                    is SignUpEvent.ShowSnackbar -> {
                        snackbarHostState.showSnackbar(event.text)
                    }
                }
            }
        }
        SnackbarComponent(snackbarHostState)
    }
}

@Composable
fun SignUpForm(
    enabled: Boolean,
    isLoading: Boolean,
    email: String,
    password: String,
    confirmPassword: String,
    isIncorrectData: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onSignUpClick: KFunction1<String, Unit>
) {

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 50.dp),
        horizontalAlignment = Alignment.End
    ) {

        val focusedField = remember { mutableStateOf(FocusedField.NONE) }

        CustomTextField(
            modifier = Modifier,
            focusedField = focusedField.value,
            onFocusChanged = { field ->
                if (field == FocusedField.EMAIL) focusedField.value = FocusedField.EMAIL
            },
            enabled = enabled,
            value = email,
            onValueChange = onEmailChange,
            labelText = stringResource(MR.strings.email),
            leadingIcon = Icons.Filled.Email,
            textStyle1 = CustomTypography().body1,
            textStyle2 = CustomTypography().body2,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            modifier = Modifier,
            focusedField = focusedField.value,
            onFocusChanged = { field ->
                if (field == FocusedField.PASSWORD) focusedField.value = FocusedField.PASSWORD
            },
            enabled = enabled,
            value = password,
            onValueChange = onPasswordChange,
            labelText = stringResource(MR.strings.password),
            leadingIcon = Icons.Filled.Lock,
            visualTransformation = PasswordVisualTransformation(),
            textStyle1 = CustomTypography().body1,
            textStyle2 = CustomTypography().body2,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            modifier = Modifier,
            focusedField = focusedField.value,
            onFocusChanged = { field ->
                if (field == FocusedField.CONFIRMPASSWORD) focusedField.value =
                    FocusedField.CONFIRMPASSWORD
            },
            enabled = enabled,
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            labelText = stringResource(MR.strings.confirm_password),
            leadingIcon = Icons.Filled.Lock,
            visualTransformation = PasswordVisualTransformation(),
            textStyle1 = CustomTypography().body1,
            textStyle2 = CustomTypography().body2,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(40.dp))
        val userExists = stringResource(MR.strings.user_already_exists)
        LoginSignUpButton({ onSignUpClick(userExists) }, isLoading, stringResource(MR.strings.sign_up2))

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