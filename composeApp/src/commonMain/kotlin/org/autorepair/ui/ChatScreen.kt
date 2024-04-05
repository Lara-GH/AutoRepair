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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.autorepair.domain.models.chat.Message
import org.autorepair.presentation.chat.ChatEvent
import org.autorepair.presentation.chat.ChatScreenModel

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

@Composable
fun Triangle(risingToTheRight: Boolean, background: Color) {
    Box(
        Modifier
            .padding(bottom = 10.dp, start = 0.dp)
            .clip(TriangleEdgeShape(risingToTheRight))
            .background(background)
            .size(6.dp)
    )
}

class TriangleEdgeShape(val risingToTheRight: Boolean) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val trianglePath = if (risingToTheRight) {
            Path().apply {
                moveTo(x = 0f, y = size.height)
                lineTo(x = size.width, y = 0f)
                lineTo(x = size.width, y = size.height)
            }
        } else {
            Path().apply {
                moveTo(x = 0f, y = 0f)
                lineTo(x = size.width, y = size.height)
                lineTo(x = 0f, y = size.height)
            }
        }

        return Outline.Generic(path = trianglePath)
    }
}

@Composable
fun ChatMessage(isMyMessage: Boolean, message: String, currentDateTime: String) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (isMyMessage) Alignment.CenterEnd else Alignment.CenterStart
    ) {

        Row(verticalAlignment = Alignment.Bottom) {
            if (!isMyMessage) {
                Column {
//                    UserPic(message.user)
                }
                Spacer(Modifier.size(2.dp))
                Column {
                    Triangle(true, MaterialTheme.colorScheme.secondary)
                }
            }

            Column {
                Box(
                    Modifier.clip(
                        RoundedCornerShape(
                            10.dp,
                            10.dp,
                            if (!isMyMessage) 10.dp else 0.dp,
                            if (!isMyMessage) 0.dp else 10.dp
                        )
                    )
                        .background(color = if (!isMyMessage) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primaryContainer)
                        .padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 5.dp),
                ) {
                    Column {
                        if (!isMyMessage) {
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(
                                    text = "BodyShop",
                                    style = MaterialTheme.typography.bodyMedium
                                        .copy(
                                            fontWeight = FontWeight.SemiBold,
                                            letterSpacing = 0.sp,
                                            fontSize = 14.sp
                                        ),
                                    color = MaterialTheme.colorScheme.background
                                )
                            }
                        }
                        Spacer(Modifier.size(3.dp))
                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 18.sp,
                                letterSpacing = 0.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                        Spacer(Modifier.size(4.dp))
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text(
                                text = currentDateTime,
                                textAlign = TextAlign.End,
                                style = MaterialTheme.typography.titleMedium
                                    .copy(fontSize = 10.sp),
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                }
                Box(Modifier.size(10.dp))
            }
            if (isMyMessage) {
                Column {
                    Triangle(false, MaterialTheme.colorScheme.primaryContainer)
                }
            }
        }
    }
}

@Composable
fun Messages(messages: List<Message>) {
    val listState = rememberLazyListState()
    if (messages.isNotEmpty()) {
        LaunchedEffect(messages.last()) {
            listState.animateScrollToItem(messages.lastIndex, scrollOffset = 2)
        }
    }
    Box(
        Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 4.dp, end = 4.dp, bottom = 50.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            state = listState
        ) {
            item { Spacer(Modifier.size(20.dp)) }
            items(messages, key = { it.currentDateTime }) {
                ChatMessage(isMyMessage = it.userRole == "user", it.message, it.currentDateTime)
            }
            item {
                Box(Modifier.height(70.dp))
            }
        }
    }
}
