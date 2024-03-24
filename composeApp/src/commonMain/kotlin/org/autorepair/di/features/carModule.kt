package org.autorepair.di.features

import org.autorepair.domain.repository.CarRepository
import org.autorepair.data.repository.CarRepositoryImpl
import org.autorepair.presentation.addcar.AddCarScreenModel
import org.koin.dsl.module

val carModule = module {

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