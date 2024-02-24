package org.autorepair.di

import org.autorepair.data.storage.dataStore
import org.koin.dsl.module

val storageModule = module {
    single {
        dataStore()
    }
}