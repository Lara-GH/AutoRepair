package org.autorepair.data.storages

import org.autorepair.domain.models.UserCar
import org.autorepair.domain.models.UserRole

interface UserCache {
    suspend fun setUserId(id: String)
    suspend fun setUserRole(role: UserRole)
    suspend fun setUserFirstName(firstName: String)
    suspend fun setUserLastName(lastName: String)
    suspend fun setUserAddress(address: String)
    suspend fun setUserEmail(email: String)
    suspend fun setUserPhone(phone: String)
    suspend fun setSelectedCarId(id: String)
    suspend fun setUserCars(cars: List<UserCar>)
    suspend fun getUserId(): String?
    suspend fun getUserRole(): UserRole?
    suspend fun getUserFirstName(): String?
    suspend fun getUserLastName(): String?
    suspend fun getUserAddress(): String?
    suspend fun getUserEmail(): String?
    suspend fun getUserPhone(): String?
    suspend fun getSelectedCarId(): String?
    suspend fun getSelectedCar(): UserCar?
    suspend fun getUserCars(): List<UserCar>
    suspend fun clearAll()
}