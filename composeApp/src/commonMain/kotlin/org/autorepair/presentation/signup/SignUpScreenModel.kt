package org.autorepair.presentation.signup

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SignUpScreenModel() : StateScreenModel<SignUpState>(SignUpState.Init) {

    private val mutableEvent: MutableSharedFlow<SignUpEvent> = MutableSharedFlow()
    val events: SharedFlow<SignUpEvent> = mutableEvent.asSharedFlow()

    fun onEmailChanged(email: String) {
        mutableState.value = mutableState.value.copy(email = email)
    }

    fun onPassChanged(pass: String) {
        mutableState.value = mutableState.value.copy(pass = pass)
    }

    fun onSignUpClick() {
        screenModelScope.launch {
            mutableEvent.emit(SignUpEvent.NavigateToHome)
        }
    }

    fun onSignInClick() {
        screenModelScope.launch {
            mutableEvent.emit(SignUpEvent.NavigateToLogin)
        }
    }
}