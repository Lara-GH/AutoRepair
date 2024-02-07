package org.autorepair.di

import org.autorepair.kmp.di.authModule
import org.autorepair.kmp.di.carModule
import org.autorepair.kmp.di.networkModule
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
