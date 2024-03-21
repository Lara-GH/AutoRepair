package org.autorepair.presentation.chat

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.database.ChildEvent
import dev.gitlive.firebase.database.DataSnapshot
import dev.gitlive.firebase.database.DatabaseReference
import dev.gitlive.firebase.database.Query
import dev.gitlive.firebase.database.database
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.autorepair.data.models.Message
import org.autorepair.data.models.YearManufacturesNew
import org.autorepair.domian.repository.ChatRepository
import org.autorepair.domian.repository.UserRepository
import org.autorepair.ui.datetime.DateTime

class ChatScreenModel(
    private val chatRepository: ChatRepository,
    private val userRepository: UserRepository
) : StateScreenModel<ChatState>(ChatState.Init) {

    private val mutableEvent: MutableSharedFlow<ChatEvent> = MutableSharedFlow()
    val events: SharedFlow<ChatEvent> = mutableEvent.asSharedFlow()

    init {
        getUserId()
    }

    private fun getUserId() {
        screenModelScope.launch {
            val userId = userRepository.getUserId().getOrNull()
            if (userId != null) {
                mutableState.value = mutableState.value.copy(userId = userId)
                onNewMessagesSubscribe()
            }
        }
    }

    fun onSendMessageClick() {
        screenModelScope.launch {
            mutableState.value = mutableState.value.copy(isLoading = true)

            val currentDateTime = DateTime.getFormattedDate()

            chatRepository.sendMessage(
                message = Message(
                    userId = mutableState.value.userId,
                    currentDateTime = currentDateTime,
                    userRole = "user",
                    message = mutableState.value.message,
                    isSeen = false
                )
            )
                .onSuccess {
                    println("!!!!!!!!!!!!!!!!!!!!!!!!UserId   ${mutableState.value.userId}")
                    println("!!!!!!!!Message sent")
                }
                .onFailure {
                    print("!!!!!!!!!NO Message sent")
                }

            mutableState.value = mutableState.value.copy(isLoading = false)
        }
    }

    private fun onNewMessagesSubscribe() {
        val query: DatabaseReference = Firebase.database.reference()
        screenModelScope.launch {
            query.child("chat").child(mutableState.value.userId).childEvents()
                .collect { event ->
                    when (event.type) {
                        ChildEvent.Type.ADDED -> {
                            val snapshot = event.snapshot
                            println("!!!!!!!!!!! New message ADDED")



                            val message = chatRepository.newMessageAdded(snapshot.value.toString())

                            message.onSuccess {
                                if (it != null) {
                                    println("!!!!!!!!!! New message IS ${it.message}")
                                    mutableState.value.messages.add(it)
                                }
                            }
                        }

                        ChildEvent.Type.CHANGED -> {
                            val snapshot = event.snapshot
                            println("!!!!!!!!!!! New message CHANGED")
                        }

                        ChildEvent.Type.REMOVED -> {
                            val snapshot = event.snapshot
                            println("!!!!!!!!!!! New message REMOVED")
                        }

                        ChildEvent.Type.MOVED -> {
                            val snapshot = event.snapshot
                            println("!!!!!!!!!!! New message MOVED")
                        }
                    }
                }
        }
    }
}