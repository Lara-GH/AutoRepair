package org.autorepair.di

import org.autorepair.presentation.home.HomeScreenModel
import org.autorepair.presentation.settings.SettingsScreenModel
import org.autorepair.presentation.splash.SplashScreenModel
import org.koin.dsl.module

val viewModelModule = module {

    factory { SplashScreenModel(
        authRepository = get()
    ) }

    factory { HomeScreenModel() }

    factory { SettingsScreenModel(
        authRepository = get()
    ) }
}