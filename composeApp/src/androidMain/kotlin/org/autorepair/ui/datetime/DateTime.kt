package org.autorepair.ui.datetime

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

actual object DateTime {
    actual fun getCurrentDateTime(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss-SSS")
        return LocalDateTime.now().format(formatter)
    }

    actual fun getFormattedDateTime(inputString: String): String {
        val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss-SSS")
        val outputFormat = DateTimeFormatter.ofPattern("h:mm a")
        return try {
            LocalDateTime.parse(inputString, inputFormat).format(outputFormat)
        } catch (e: Exception) {
            "Invalid date format"
        }
    }
}