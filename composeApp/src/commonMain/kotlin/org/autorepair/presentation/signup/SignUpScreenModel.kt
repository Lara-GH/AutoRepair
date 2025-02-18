package org.autorepair.presentation.signup

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.autorepair.domain.models.UserRole
import org.autorepair.domain.repository.AuthRepository
import org.autorepair.domain.repository.UserRepository

class SignUpScreenModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : StateScreenModel<SignUpState>(SignUpState.Init) {

    private val mutableEvent: MutableSharedFlow<SignUpEvent> = MutableSharedFlow()
    val events: SharedFlow<SignUpEvent> = mutableEvent.asSharedFlow()

    fun onEmailChanged(email: String) {
        mutableState.value = mutableState.value.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        mutableState.value = mutableState.value.copy(password = password)
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        mutableState.value = mutableState.value.copy(confirmPassword = confirmPassword)
    }

    fun onSignUpClick(passwordMismatch: String) {
        screenModelScope.launch {
            val state = mutableState.value
            if (state.password == state.confirmPassword) {
                mutableState.value = mutableState.value.copy(
                    isLoading = true,
                    isPasswordMismatch = false,
                    error = null
                )

                authRepository.createUser(email = state.email, password = state.password)
                    .onSuccess { user ->
                        userRepository.setUserId(user.id)
                        userRepository.setUserRole(user.role)
                        userRepository.setUserEmail(user.email)
                        userRepository.syncPushToken()
                        userRepository.addUserToRealtimeDatabase(user.id, user.role, user.email)
                            .onSuccess {
                                print("user = ${user.id} has role = ${user.role}")
                                val currentUser = authRepository.getCurrentUser().getOrNull()
                                val event = when (currentUser?.role) {
                                    UserRole.MANAGER -> SignUpEvent.NavigateToManagerHome
                                    UserRole.MECHANIC -> SignUpEvent.NavigateToMechanicHome
                                    UserRole.USER -> SignUpEvent.NavigateToUserHome
                                    else -> SignUpEvent.NavigateToUserHome
                                }
                                mutableEvent.emit(event)
                            }.onFailure { error ->
                            error.message?.let { message ->
                                mutableEvent.emit(SignUpEvent.ShowSnackBar(message))
                            }
                        }
                    }.onFailure { error ->
                        error.message?.let { message ->
                            mutableEvent.emit(SignUpEvent.ShowSnackBar(message))
                        }
                    }
                mutableState.value = mutableState.value.copy(isLoading = false)
            } else {
                mutableState.value = mutableState.value.copy(isPasswordMismatch = true)
                mutableEvent.emit(SignUpEvent.ShowSnackBar(passwordMismatch))
            }
        }
    }

    fun onSignInClick() {
        screenModelScope.launch {
            mutableEvent.emit(SignUpEvent.NavigateToLogin)
        }
    }
}