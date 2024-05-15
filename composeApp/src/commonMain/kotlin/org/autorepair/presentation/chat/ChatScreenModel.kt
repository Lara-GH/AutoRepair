package org.autorepair.presentation.chat

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.autorepair.data.exceptions.UnathorizedException
import org.autorepair.domain.models.UserRole
import org.autorepair.domain.models.chat.ObserveChatEvent
import org.autorepair.domain.repository.ChatRepository
import org.autorepair.domain.repository.UserRepository

class ChatScreenModel(
    private val userId: String,
    private val chatRepository: ChatRepository,
    private val userRepository: UserRepository
) : StateScreenModel<ChatState>(ChatState.Init) {

    private val mutableEvent: MutableSharedFlow<ChatEvent> = MutableSharedFlow()
    val events: SharedFlow<ChatEvent> = mutableEvent.asSharedFlow()

    init {
        println("ChatScreenModel created $this!!!!! userId = $userId")
        onNewMessagesSubscribe()
    }

    fun onSendMessageClick() {
        screenModelScope.launch {
            mutableState.value = mutableState.value.copy(isLoading = true)

            val messageText = mutableState.value.message
            mutableState.value = mutableState.value.copy(message = "")

            chatRepository.sendMessage(
                userChatID = userId,
                messageText = messageText
            )
                .onSuccess {}
                .onFailure {
                    println("!!!!!!!!!NO Message sent, $it")
                    if (it is UnathorizedException) {
                        mutableEvent.emit(ChatEvent.ShowSnackbar("unauth"))
                        delay(2000)
                        mutableEvent.emit(ChatEvent.NavigateToLogin)
                    }
                }

            mutableState.value = mutableState.value.copy(isLoading = false)
        }
    }

    private fun onNewMessagesSubscribe() {
        screenModelScope.launch {
            userRepository.getUserRole().onSuccess { userRole ->
                userRole?.let { userRole ->
                    when (userRole) {
                        UserRole.MANAGER -> {
                                chatRepository.observeCurrentUserChatEvents(
                                    userRole,
                                    userId
                                )
                                    .collect { event ->
                                        when (event) {
                                            is ObserveChatEvent.MessageAdded -> {
                                                mutableState.value = mutableState.value.copy(
                                                    messages = mutableState.value.messages + event.message
                                                )
                                            }
                                        }
                                    }
                            }

                        UserRole.MECHANIC -> {}
                        else -> {
                            chatRepository.observeCurrentUserChatEvents(userRole, "")
                                .collect { event ->
                                    when (event) {
                                        is ObserveChatEvent.MessageAdded -> {
                                            mutableState.value = mutableState.value.copy(
                                                messages = mutableState.value.messages + event.message
                                            )
                                        }
                                    }
                                }
                        }
                    }
                }
            }
        }
    }

    fun onMessageChanged(message: String) {
        mutableState.value = mutableState.value.copy(message = message)
    }

    override fun onDispose() {
        println()
        println("onDispose ChatScreenModel!!!!!")
        super.onDispose()
    }
}