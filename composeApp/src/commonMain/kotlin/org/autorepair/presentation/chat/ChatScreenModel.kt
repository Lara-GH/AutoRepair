package org.autorepair.presentation.chat

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.autorepair.data.exceptions.UnathorizedException
import org.autorepair.data.models.chat.FirebaseMessage
import org.autorepair.domain.models.chat.Message
import org.autorepair.domain.models.chat.ObserveChatEvent
import org.autorepair.domain.repository.ChatRepository
import org.autorepair.domain.repository.UserRepository
import org.autorepair.ui.datetime.DateTime

class ChatScreenModel(
    private val chatRepository: ChatRepository,
) : StateScreenModel<ChatState>(ChatState.Init) {

    private val mutableEvent: MutableSharedFlow<ChatEvent> = MutableSharedFlow()
    val events: SharedFlow<ChatEvent> = mutableEvent.asSharedFlow()

    init {
        mutableState.value = mutableState.value.copy(message = "Hi! How are you?")
        onNewMessagesSubscribe()
    }

    fun onSendMessageClick() {
        screenModelScope.launch {
            mutableState.value = mutableState.value.copy(isLoading = true)

            chatRepository.sendMessage(
                messageText = mutableState.value.message
            )
                .onSuccess {
                    println("!!!!!!!!Message sent")
                }
                .onFailure {
                    println("!!!!!!!!!NO Message sent, $it")
                    if(it is UnathorizedException) {
                        mutableEvent.emit(ChatEvent.ShowSnackbar("unauth"))
                        delay(2000)
                        mutableEvent.emit(ChatEvent.NavigateToLogin)
                    }
//                    toast
//                    snackbar
                }

            mutableState.value = mutableState.value.copy(isLoading = false)
        }
    }

    private fun onNewMessagesSubscribe() {
        screenModelScope.launch {
            chatRepository.observeCurrentUserChatEvents()
                .collect {
                    when (it) {
                        is ObserveChatEvent.MessageAdded -> {
                            mutableState.value = mutableState.value.copy(
                                messages = mutableState.value.messages + it.message
                            )
                        }
                    }
                }
        }
    }
}