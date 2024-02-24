package org.autorepair.data.storages

import org.autorepair.domian.models.UserCar

interface UserCache {
    suspend fun setSelectedCarId(id: String)
    fun setUserCars(cars: List<UserCar>)

    suspend fun getSelectedCarId(): String?
    fun getSelectedCar(): UserCar?
    fun getUserCars(): List<UserCar>
}