package org.autorepair.di.features

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.database.database
import org.autorepair.data.repository.ChatRepositoryImpl
import org.autorepair.domian.repository.ChatRepository
import org.autorepair.presentation.chat.ChatScreenModel
import org.koin.dsl.module

val chatModule = module {

    factory {
        ChatScreenModel(
            chatRepository = get(),
            userRepository = get()
        )
    }

    factory<ChatRepository> {
        ChatRepositoryImpl(
            databaseReference = Firebase.database.reference(),
            json = get()
        )
    }
}