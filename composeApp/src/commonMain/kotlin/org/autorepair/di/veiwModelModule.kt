package org.autorepair.kmp.di

import org.autorepair.presentation.splash.SplashScreenModel
import org.koin.dsl.module

val viewModelModule = module {

    factory { SplashScreenModel(
        authRepository = get()
    ) }
}