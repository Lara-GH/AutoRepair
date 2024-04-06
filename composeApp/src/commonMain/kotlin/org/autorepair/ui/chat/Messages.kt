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
import org.autorepair.domain.models.chat.Message


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
            items(messages, key = { it.id }) {
                ChatMessage(isMyMessage = it.isMine, it.message, it.currentDateTime)
            }
            item {
                Box(Modifier.height(70.dp))
            }
        }
    }
}