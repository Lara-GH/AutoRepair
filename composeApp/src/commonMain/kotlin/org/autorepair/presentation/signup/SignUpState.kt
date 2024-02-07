package org.autorepair.presentation.signup

data class SignUpState(
    val formEnabled: Boolean,
    val email: String,
    val pass: String,
    val isLoading: Boolean,
    val error: Throwable?,
    val isIncorrectData: Boolean
) {
    companion object {
        val Init = SignUpState(
            formEnabled = true,
            email = "",
            pass = "",
            isLoading = false,
            error = null,
            isIncorrectData = false
        )
    }
}