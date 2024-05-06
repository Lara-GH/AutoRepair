package org.autorepair.presentation.chatlist

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.autorepair.domain.repository.ChatRepository

class ChatListScreenModel(
    private val chatRepository: ChatRepository
) : StateScreenModel<ChatListState>(ChatListState.Init) {

    private val mutableEvent: MutableSharedFlow<ChatListEvent> = MutableSharedFlow()
    val events: SharedFlow<ChatListEvent> = mutableEvent.asSharedFlow()

    init {
        onChatsSubscribe()
    }

    private fun onChatsSubscribe() {
        screenModelScope.launch {
            chatRepository.observeChatsEvents()
                .collect { event ->
                    mutableState.update { it.copy(chats = it.chats + event) }
                }
        }
    }

    fun onChatClick(userID: String) {
        screenModelScope.launch {
            chatRepository.setUserChatID(userID).onSuccess {
                mutableEvent.emit(ChatListEvent.NavigateToChat)
            }
        }
    }
}