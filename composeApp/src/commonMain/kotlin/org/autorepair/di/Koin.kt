package org.autorepair.di

import org.autorepair.di.features.authModule
import org.autorepair.di.features.carModule
import org.autorepair.di.features.chatModule
import org.autorepair.di.features.userModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun initKoin(modules: List<Module> = emptyList()) {
    startKoin {
        modules(
            authModule,
            userModule,
            carModule,
            networkModule,
            viewModelModule,
            chatModule
        )
        modules(platformModules() + modules)
    }
}

fun initKoinIos() = initKoin()
expect fun platformModules(): List<Module>
