package org.autorepair.kmp.di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.autorepair.data.repository.auth.AuthRepository
import org.autorepair.data.repository.auth.AuthRepositoryImpl
import org.autorepair.presentation.login.LoginScreenModel
import org.koin.dsl.module

val authModule = module {

    factory <AuthRepository>{
        AuthRepositoryImpl(
            auth = Firebase.auth
        )
    }

    factory { LoginScreenModel(
        authRepository = get(),
        carRepository = get()
    ) }

}