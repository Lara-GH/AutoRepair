package org.autorepair.presentation.settings

data class SettingsState(
    val isLoading: Boolean,
    val error: Throwable?
) {
    companion object {
        val Init = SettingsState(
            isLoading = false,
            error = null
        )
    }
}