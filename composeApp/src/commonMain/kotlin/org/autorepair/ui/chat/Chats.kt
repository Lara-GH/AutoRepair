package org.autorepair.ui.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Chats(chats: List<String>, onClick: (String) -> Unit) {
    val listState = rememberLazyListState()
    if (chats.isNotEmpty()) {
        LaunchedEffect(chats.last()) {
            listState.animateScrollToItem(chats.lastIndex, scrollOffset = 2)
        }
    }
    Box(
        Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 4.dp, end = 4.dp, bottom = 50.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp),
            state = listState
        ) {
            item { Spacer(Modifier.size(20.dp)) }
            items(chats, key = { it }) {
                UserChatItem(userID = it, onClick = { userID -> onClick(userID) })
            }
            item {
                Box(Modifier.height(70.dp))
            }
        }
    }
}