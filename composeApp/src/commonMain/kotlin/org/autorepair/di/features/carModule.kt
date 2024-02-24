package org.autorepair.di.features

import org.autorepair.domian.repository.CarRepository
import org.autorepair.data.repository.CarRepositoryImpl
import org.autorepair.data.storages.UserCache
import org.autorepair.data.storages.UserCacheImpl
import org.autorepair.presentation.addcar.AddCarScreenModel
import org.koin.dsl.module

val carModule = module {

    factory<UserCache> {
        UserCacheImpl(
            dataStore = get(),
            json = get()
        )
    }

    factory<CarRepository> {
        CarRepositoryImpl(
            ktorClient = get(),
            json = get(),
            userCache = get()
        )
    }

    factory {
        AddCarScreenModel(
            carRepository = get()
        )
    }
}