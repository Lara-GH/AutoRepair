package org.autorepair.ui.datetime

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter

actual object DateTime {

    actual fun getCurrentDateTime(): String {
        val dateFormatter = NSDateFormatter().apply {
            dateFormat = "yyyy-MM-dd HH-mm-ss-SSS"
        }
        return dateFormatter.stringFromDate(NSDate())
    }

    actual fun getFormattedDateTime(inputString: String): String {
        val dateFormatter = NSDateFormatter().apply {
            dateFormat = "yyyy-MM-dd HH-mm-ss-SSS"
        }

        val date = dateFormatter.dateFromString(inputString)

        val timeFormatter = NSDateFormatter().apply {
            dateFormat = "h:mm a"
        }

        return date?.let { timeFormatter.stringFromDate(it) } ?: "Invalid date format"
    }
}