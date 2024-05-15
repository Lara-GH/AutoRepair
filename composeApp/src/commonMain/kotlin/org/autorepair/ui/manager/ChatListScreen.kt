package org.autorepair.ui.manager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.autorepair.presentation.chatlist.ChatListEvent
import org.autorepair.presentation.chatlist.ChatListScreenModel
import org.autorepair.ui.ChatScreen
import org.autorepair.ui.chat.Chats

@Composable
fun Screen.ChatListContent() {

    val screenModel = getScreenModel<ChatListScreenModel>()
    val state by screenModel.state.collectAsState()
    val parentNavigator = LocalNavigator.currentOrThrow.parent ?: error("No parent navigator")

    LaunchedEffect(true) {
        screenModel.events.collect {
            when (it) {
                is ChatListEvent.NavigateToChat -> parentNavigator.push(ChatScreen(it.userId))
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
            Chats(state.chats) { userID -> screenModel.onChatClick(userID) }
        }
    }
}

