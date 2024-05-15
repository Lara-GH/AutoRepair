package org.autorepair.di.features

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.database.database
import org.autorepair.data.repository.ChatRepositoryImpl
import org.autorepair.domain.repository.ChatRepository
import org.autorepair.presentation.chat.ChatScreenModel
import org.autorepair.presentation.chatlist.ChatListScreenModel
import org.koin.dsl.module

val chatModule = module {

    factory {
        ChatScreenModel(
            userId = it.get(),
            chatRepository = get(),
            userRepository = get()
        )
    }
    factory {
        ChatListScreenModel(
            chatRepository = get()
        )
    }

    factory {
        Firebase.database.reference()
    }

    factory<ChatRepository> {
        ChatRepositoryImpl(
            databaseReference = get(),
            userCache = get(),
            ktorClient = get()
        )
    }
}