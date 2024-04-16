package org.autorepair.di.features

import org.autorepair.data.repository.UserRepositoryImpl
import org.autorepair.data.storages.UserCache
import org.autorepair.data.storages.UserCacheImpl
import org.autorepair.domain.repository.UserRepository
import org.koin.dsl.module

val userModule = module {

    factory<UserCache> {
        UserCacheImpl(
            dataStore = get(),
            json = get()
        )
    }

    factory <UserRepository>{
        UserRepositoryImpl(
            userCache = get(),
            databaseReference = get()
        )
    }
}