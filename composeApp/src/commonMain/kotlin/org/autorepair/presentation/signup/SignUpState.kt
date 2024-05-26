package org.autorepair.presentation.signup

data class SignUpState(
    val formEnabled: Boolean,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val isLoading: Boolean,
    val error: Throwable?,
    val isIncorrectData: Boolean,
    val isUserExists: Boolean
) {
    companion object {
        val Init = SignUpState(
            formEnabled = true,
            email = "",
            password = "",
            confirmPassword = "",
            isLoading = false,
            error = null,
            isIncorrectData = false,
            isUserExists = false
        )
    }
}