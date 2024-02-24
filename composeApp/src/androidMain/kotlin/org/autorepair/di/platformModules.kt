package org.autorepair.di

import org.koin.core.module.Module

actual fun platformModules(): List<Module> {
    return listOf(storageModule)
}