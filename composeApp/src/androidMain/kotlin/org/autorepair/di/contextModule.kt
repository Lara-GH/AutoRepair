package org.autorepair.di

import android.content.Context
import org.koin.dsl.module

fun contextModule(context: Context) = module {
    factory<Context> { context }
}