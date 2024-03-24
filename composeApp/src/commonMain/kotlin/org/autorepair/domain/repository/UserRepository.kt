package org.autorepair.domain.repository

interface UserRepository {
    suspend fun getUserId(): Result<String?>
    suspend fun setUserId(id: String): Result<Unit>
}