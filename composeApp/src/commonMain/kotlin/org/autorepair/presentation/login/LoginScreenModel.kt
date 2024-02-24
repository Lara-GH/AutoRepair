package org.autorepair.presentation.login

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.autorepair.data.models.IncorrectDataException
import org.autorepair.domian.repository.AuthRepository

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
                    mutableEvent.emit(LoginEvent.NavigateToHome)
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