package org.autorepair.data

import org.autorepair.data.models.User

interface AuthRepository {
    suspend fun auth(email: String, password: String) : Result<User>
    suspend fun getCurrentUser(): Result<User?>
    suspend fun logout(): Result<Unit>
}