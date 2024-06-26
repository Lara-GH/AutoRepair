package org.autorepair.di.features

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.autorepair.data.repository.AuthRepositoryImpl
import org.autorepair.domain.repository.AuthRepository
import org.autorepair.presentation.login.LoginScreenModel
import org.autorepair.presentation.signup.SignUpScreenModel
import org.koin.dsl.module

val authModule = module {

    factory<AuthRepository> {
        AuthRepositoryImpl(
            auth = Firebase.auth,
            userCache = get(),
            userRepository = get()
        )
    }

    factory {
        LoginScreenModel(
            authRepository = get(),
            userRepository = get()
        )
    }

    factory {
        SignUpScreenModel(
            authRepository = get(),
            userRepository = get()
        )
    }
}