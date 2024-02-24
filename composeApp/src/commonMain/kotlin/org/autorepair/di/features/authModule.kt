package org.autorepair.di.features

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.autorepair.domian.repository.AuthRepository
import org.autorepair.data.repository.AuthRepositoryImpl
import org.autorepair.presentation.login.LoginScreenModel
import org.koin.dsl.module

val authModule = module {

    factory <AuthRepository>{
        AuthRepositoryImpl(
            auth = Firebase.auth
        )
    }

    factory { LoginScreenModel(
        authRepository = get()
    ) }

}