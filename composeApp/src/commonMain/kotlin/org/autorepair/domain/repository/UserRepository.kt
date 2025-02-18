package org.autorepair.domain.repository

import org.autorepair.domain.models.UserRole

interface UserRepository {
    suspend fun getUserId(): Result<String?>
    suspend fun setUserId(id: String): Result<Unit>
    suspend fun getUserRole(): Result<UserRole?>
    suspend fun setUserRole(role: UserRole): Result<Unit>
    suspend fun getUserEmail(): Result<String?>
    suspend fun setUserEmail(email: String): Result<Unit>
    suspend fun getUserFirstName(): Result<String?>
    suspend fun setUserFirstName(firstName: String): Result<Unit>
    suspend fun getUserLastName(): Result<String?>
    suspend fun setUserLastName(lastName: String): Result<Unit>
    suspend fun getUserAddress(): Result<String?>
    suspend fun setUserAddress(address: String): Result<Unit>
    suspend fun getUserPhone(): Result<String?>
    suspend fun setUserPhone(phone: String): Result<Unit>
    suspend fun syncPushToken(): Result<Unit>
    suspend fun deleteMyToken(): Result<Unit>
    suspend fun addUserToRealtimeDatabase(id: String, role: UserRole, email: String): Result<Unit>
}