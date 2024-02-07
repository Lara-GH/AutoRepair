package org.autorepair.kmp.di

import org.autorepair.data.FirebaseHolder
import org.autorepair.data.repository.car.CarRepository
import org.autorepair.data.repository.car.CarRepositoryImpl
import org.autorepair.presentation.addcar.AddCarScreenModel
import org.koin.dsl.module

val carModule = module {

    factory<CarRepository>{
        CarRepositoryImpl(
            firebaseHolder = FirebaseHolder,
            ktorClient = get(),
            json = get()
        )
    }

    factory { AddCarScreenModel(
        carRepository = get()
    ) }

}