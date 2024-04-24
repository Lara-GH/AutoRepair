package org.autorepair.domain.repository

import org.autorepair.domain.models.UserRole

interface UserRepository {
    suspend fun getUserId(): Result<String?>
    suspend fun setUserId(id: String): Result<Unit>
    suspend fun getUserRole(): Result<UserRole?>
    suspend fun setUserRole(role: UserRole): Result<Unit>
    suspend fun syncPushToken(): Result<Unit>
    suspend fun deleteMyToken(): Result<Unit>
}