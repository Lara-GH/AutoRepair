package org.autorepair.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import org.autorepair.MR
import org.autorepair.presentation.splash.SplashScreenModel

object BodyShopScreen : Screen {

    @Composable
    override fun Content() {
        BodyShopContent()
    }
}

@Composable
fun Screen.BodyShopContent() {
    val screenModel = getScreenModel<SplashScreenModel>()
    val state by screenModel.state.collectAsState()

    val navigator = LocalNavigator.currentOrThrow

    Box() {
        Column(
            Modifier.fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.background
                )
        ) {
            Column(
                Modifier.fillMaxWidth().fillMaxHeight().padding(8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.background
                    ), Arrangement.Top, Alignment.CenterHorizontally
            ) {
                UserInfo()
                UserOptions()
                UserHistory()
                BottomAppBarGeneral()
            }
        }
    }
}


@Composable
fun UserInfo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        IconButton(onClick = { /* do something */ }) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Settings icon button"
            )
        }
        IconButton(onClick = { /* do something */ }) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Edit icon button"
            )
        }
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.Face,
                contentDescription = "Face icon"
            )
            Column {
                Text(
                    modifier = Modifier,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontFamily = fontFamilyResource(MR.fonts.Montserrat.extraBold),
                    text = stringResource(MR.strings.user_first_name)
                )
                Text(
                    modifier = Modifier,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontFamily = fontFamilyResource(MR.fonts.Montserrat.extraBold),
                    text = stringResource(MR.strings.user_last_name)
                )
                Text(
                    modifier = Modifier,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontFamily = fontFamilyResource(MR.fonts.Montserrat.extraBold),
                    text = stringResource(MR.strings.user_phone)
                )
            }
        }
    }
}

@Composable
fun UserOptions(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        TextField(
            value = DatePickerModal(
                onDateSelected = { Unit },
                onDismiss = { Unit }
            )
        )
        Text(
            modifier = Modifier,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondaryContainer,
            fontFamily = fontFamilyResource(MR.fonts.Montserrat.extraBold),
            text = stringResource(MR.strings.date_of_registration)
        )
        Row(verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text ="Cars",
                color = MaterialTheme.colorScheme.secondaryContainer)
            Image(
                painter = painterResource(id = R.drawable.drop_down_ic),
                contentDescription = "DropDown Icon"
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Payment",
                color = MaterialTheme.colorScheme.secondaryContainer)
            Image(
                painter = painterResource(id = R.drawable.drop_down_ic),
                contentDescription = "DropDown Icon"
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Notifications",
                color = MaterialTheme.colorScheme.secondaryContainer)
            Image(
                painter = painterResource(id = R.drawable.drop_down_ic),
                contentDescription = "DropDown Icon"
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically
        ) {
            Text("About the app",
                color = MaterialTheme.colorScheme.secondaryContainer)
            Image(
                painter = painterResource(id = R.drawable.drop_down_ic),
                contentDescription = "DropDown Icon"
            )
        }
    }
}

@Composable
fun UserHistory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Row {
            Text(
                modifier = Modifier,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primaryContainer,
                fontFamily = fontFamilyResource(MR.fonts.Montserrat.extraBold),
                text = stringResource(MR.strings.History)
            )
            Text(
                modifier = Modifier,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primaryContainer,
                fontFamily = fontFamilyResource(MR.fonts.Montserrat.extraBold),
                text = stringResource(MR.strings.Appointments)
            )
        }
        Text(
            modifier = Modifier,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primaryContainer,
            fontFamily = fontFamilyResource(MR.fonts.Montserrat.extraBold),
            text = stringResource(MR.strings.story_empty)
        )
    }
}


@Composable
fun BottomAppBarGeneral() {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home icon button"
                        )
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Cars icon button",
                        )
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Shop icon button",
                        )
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Profile icon button"
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { /* do something */ },
                        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            "Chat Profile icon button"
                        )
                    }
                }
            )
        },
        content = { Unit }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
