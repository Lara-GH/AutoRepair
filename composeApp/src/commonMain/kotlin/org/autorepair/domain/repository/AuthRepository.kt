package org.autorepair.domain.repository

import org.autorepair.domain.models.User

interface AuthRepository {
    suspend fun createUser(email: String, password: String) : Result<User>
    suspend fun auth(email: String, password: String) : Result<User>
    suspend fun getCurrentUser(): Result<User?>
    suspend fun logout(): Result<Unit>
}