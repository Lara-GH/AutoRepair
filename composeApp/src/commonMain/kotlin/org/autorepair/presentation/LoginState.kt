package org.autorepair.presentation

data class LoginState(
    val formEnabled: Boolean,
    val email: String,
    val pass: String,
    val isLoading: Boolean,
    val error: Throwable?
) {
    companion object {
        val Init = LoginState(
            formEnabled = true,
            email = "",
            pass = "",
            isLoading = false,
            error = null
        )
    }
}