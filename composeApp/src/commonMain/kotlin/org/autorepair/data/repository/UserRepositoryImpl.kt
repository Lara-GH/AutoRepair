package org.autorepair.data.repository

import org.autorepair.data.storages.UserCache
import org.autorepair.domain.models.UserRole
import org.autorepair.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userCache: UserCache
) : UserRepository {
    override suspend fun getUserId(): Result<String?> {
        return try {
            val userId = userCache.getUserId()
            println("!!!!!!!!!!!!!!!!!!!!!!!!getUserId   $userId")
            Result.success(userId)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    override suspend fun setUserId(id: String): Result<Unit> {
        return try {
            userCache.setUserId(id)
            println("!!!!!!!!!!!!!!!!!!!!!!!!setUserId   $id")
            Result.success(Unit)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    override suspend fun getUserRole(): Result<UserRole?> {
        return try {
            val userRole = userCache.getUserRole()
            println("!!!!!!!!!!!!!!!!!!!!!!!!getUserRole   $userRole")
            Result.success(userRole)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    override suspend fun setUserRole(role: UserRole): Result<Unit> {
        return try {
            userCache.setUserRole(role)
            println("!!!!!!!!!!!!!!!!!!!!!!!!setUserIRole   $role")
            Result.success(Unit)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}