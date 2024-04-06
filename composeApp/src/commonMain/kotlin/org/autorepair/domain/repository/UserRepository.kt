package org.autorepair.domain.repository

interface UserRepository {
    suspend fun getUserId(): Result<String?>
    suspend fun setUserId(id: String): Result<Unit>
    suspend fun getUserRole(): Result<String?>
    suspend fun setUserRole(role: String): Result<Unit>
}