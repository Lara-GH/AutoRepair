package org.autorepair.presentation.login

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.autorepair.data.models.IncorrectDataException
import org.autorepair.domian.models.UserRole
import org.autorepair.domian.repository.AuthRepository
import org.autorepair.presentation.splash.SplashEvent

class LoginScreenModel(
    private val authRepository: AuthRepository
) : StateScreenModel<LoginState>(LoginState.Init) {

    private val mutableEvent: MutableSharedFlow<LoginEvent> = MutableSharedFlow()
    val events: SharedFlow<LoginEvent> = mutableEvent.asSharedFlow()

    fun onEmailChanged(email: String) {
        mutableState.value = mutableState.value.copy(email = email)
    }

    fun onPassChanged(pass: String) {
        mutableState.value = mutableState.value.copy(pass = pass)
    }

    fun onLoginClick() {
        screenModelScope.launch {

            mutableState.value = mutableState.value.copy(
                isLoading = true,
                isIncorrectData = false,
                error = null
            )
            val state = mutableState.value
            authRepository.auth(email = state.email, password = state.pass)
                .onSuccess {
                    print("user = ${it.id} has role = ${it.role}")
                    val user = authRepository.getCurrentUser().getOrNull()
                    val event = if (user != null) {
                        when (user.role) {
                            UserRole.MANAGER -> LoginEvent.NavigateToUserHome
                            UserRole.MECHANIC -> LoginEvent.NavigateToMechanicHome
                            UserRole.USER -> LoginEvent.NavigateToUserHome
                            // TODO manager home
                        }
                    } else {
                        LoginEvent.NavigateToUserHome
                    }
                    mutableEvent.emit(event)
                }
                .onFailure {
                    mutableState.value = mutableState.value.copy(
                        error = it,
                        isIncorrectData = it is IncorrectDataException
                    )
                }
            mutableState.value = mutableState.value.copy(isLoading = false)
        }
    }

    fun onSingUpClick() {
        screenModelScope.launch {
            mutableEvent.emit(LoginEvent.NavigateToSignUp)
        }
    }
}