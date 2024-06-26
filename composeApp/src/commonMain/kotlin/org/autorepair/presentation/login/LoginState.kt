package org.autorepair.presentation.login

data class LoginState(
    val formEnabled: Boolean,
    val email: String,
    val password: String,
    val isLoading: Boolean,
    val error: Throwable?
) {
    companion object {
        val Init = LoginState(
            formEnabled = true,
            email = "",
            password = "",
            isLoading = false,
            error = null
        )
    }
}