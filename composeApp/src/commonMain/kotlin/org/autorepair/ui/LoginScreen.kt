package org.autorepair.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.Typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.stringResource
import org.autorepair.MR
import org.autorepair.presentation.login.LoginEvent
import org.autorepair.presentation.login.LoginScreenModel
import org.autorepair.ui.mechanic.MechanicTabScreen

object LoginScreen : Screen {

    @Composable
    override fun Content() {
        LoginContent()
    }
}

@Composable
fun Screen.LoginContent() {
    val screenModel = getScreenModel<LoginScreenModel>()
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
            LoginHeader(modifier = Modifier.padding(bottom = 50.dp, top = 150.dp))
            LoginForm(
                enabled = state.formEnabled,
                isLoading = state.isLoading,
                email = state.email,
                password = state.pass,
                isIncorrectData = state.isIncorrectData,
                onEmailChange = screenModel::onEmailChanged,
                onPasswordChange = { screenModel.onPassChanged(it) },
                onLoginClick = screenModel::onLoginClick
            )
            LoginFooter(
                onSignUpClick = screenModel::onSingUpClick
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
                is LoginEvent.NavigateToUserHome -> navigator.replace(UserTabScreen)
                is LoginEvent.NavigateToMechanicHome -> navigator.replace(MechanicTabScreen)
                is LoginEvent.NavigateToSignUp -> navigator.push(SignUpScreen)
            }
        }
    }
}

enum class FocusedField {
    EMAIL,
    PASSWORD,
    CONFIRMPASSWORD,
    NONE;
}

@Composable
fun LoginForm(
    enabled: Boolean,
    isLoading: Boolean,
    email: String,
    password: String,
    isIncorrectData: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
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
        ),
        h1 = TextStyle(
            fontFamily = fontFamilyResource(MR.fonts.Montserrat.extraBold),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.outline,
            baselineShift = BaselineShift(-2f)
        ),
    )

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
            leadingIcon = Icons.Filled.Lock,
            textStyle1 = loginTypography.body1,
            textStyle2 = loginTypography.body2,
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
            textStyle1 = loginTypography.body1,
            textStyle2 = loginTypography.body2,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(40.dp))

        LoginButton(onLoginClick, isLoading)
    }
}

@Composable
fun WrapCard(shouldWrap: Boolean, content: @Composable () -> Unit) {
    if (shouldWrap) {
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
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = fontFamilyResource(MR.fonts.Montserrat.extraBold),
            text = stringResource(MR.strings.login_header_title)
        )

        Spacer(modifier = Modifier.height(6.dp))
        Text(
            modifier = Modifier,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.secondary,
            fontFamily = fontFamilyResource(MR.fonts.Montserrat.semiBold),
            text = stringResource(MR.strings.login_header_subtitle)
        )
    }
}

@Composable
fun LoginFooter(onSignUpClick: () -> Unit) {

    Row(
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
            .padding(bottom = 10.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        ClickableText(
            modifier = Modifier.padding(end = 5.dp),
            text = AnnotatedString(stringResource(MR.strings.dont_have_an_account)),
            style = TextStyle(
                color = MaterialTheme.colorScheme.secondary,
                fontFamily = fontFamilyResource(MR.fonts.Montserrat.bold)
            ),
            onClick = {
                onSignUpClick()
            }
        )
        ClickableText(
            text = AnnotatedString(stringResource(MR.strings.sign_up)),
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontFamily = fontFamilyResource(MR.fonts.Montserrat.bold)
            ),
            onClick = {
                onSignUpClick()
            }
        )
    }
}

@Composable
fun LoginButton(onLoginClick: () -> Unit, isLoading: Boolean) {
    Box {
        Button(
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            onClick = {
                onLoginClick()
            }
        ) {
            Row(
                modifier = Modifier.padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(MR.strings.login2),
                    color = MaterialTheme.colorScheme.background,
                    style = TextStyle(
                        fontFamily = fontFamilyResource(MR.fonts.Montserrat.semiBold),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp,
                    )
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.background
                )
            }
        }
        if (isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.align(Alignment.Center).size(30.dp)
            )
        }
    }
}

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    focusedField: FocusedField,
    onFocusChanged: (FocusedField) -> Unit,
    enabled: Boolean,
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    leadingIcon: ImageVector,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textStyle1: TextStyle,
    textStyle2: TextStyle,
    colors: TextFieldColors
) {
    WrapCard(focusedField == focusedField) {
        TextField(
            modifier = modifier.fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background)
                .onFocusChanged { onFocusChanged(focusedField) },
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.outline,
                )
            },
            visualTransformation = visualTransformation,
            enabled = enabled,
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = labelText, style = textStyle2) },
            singleLine = true,
            textStyle = textStyle1,
            colors = colors
        )
    }
}