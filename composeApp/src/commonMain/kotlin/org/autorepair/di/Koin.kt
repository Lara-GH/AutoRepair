package org.autorepair.di

import org.autorepair.di.features.authModule
import org.autorepair.di.features.carModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun initKoin(modules: List<Module> = emptyList()){
    startKoin{
        modules(
            carModule,
            networkModule,
            authModule,
            viewModelModule
        )
        modules(platformModules() + modules)
    }
}

expect fun platformModules(): List<Module>
