package org.autorepair.data.repository.auth

import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseAuthInvalidUserException
import org.autorepair.data.FirebaseHolder
import org.autorepair.data.models.IncorrectDataException
import org.autorepair.data.models.User

class AuthRepositoryImpl(
    private val auth: FirebaseAuth = FirebaseHolder.auth
) : AuthRepository {

    //gomellora@gmail.com
    //Qaz12345
    override suspend fun auth(email: String, password: String): Result<User> {
        return runCatching {
            val result = auth.signInWithEmailAndPassword(email, password)
            val resultUser = result.user
            if (resultUser != null) {
                return Result.success(User(resultUser.uid))
            } else {
                return Result.failure(IncorrectDataException())
            }
        }.onFailure {
            if (it is FirebaseAuthInvalidUserException) {
                return Result.failure(IncorrectDataException())
            }
        }
    }

    override suspend fun getCurrentUser(): Result<User?> {
        return runCatching {
            val firebaseUser = auth.currentUser
            firebaseUser?.let { User(firebaseUser.uid) }
        }
    }

    override suspend fun logout(): Result<Unit> {
        return runCatching { auth.signOut() }
    }
}