package org.autorepair.kmp.di

import org.koin.core.context.startKoin

fun initKoin(){
    startKoin{
        modules(
            carModule,
            networkModule,
            authModule,
            viewModelModule
        )
    }
}
