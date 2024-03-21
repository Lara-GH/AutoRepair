package org.autorepair.ui.datetime

import org.autorepair.data.exceptions.DataTimeException
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter

actual object DateTime {
    actual fun getFormattedDate(): String {

        val dateFormatter = NSDateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd HH-mm-ss-SSS"
        try {
            return dateFormatter.stringFromDate(NSDate())
        } catch (e: DataTimeException) {
            print(e.message)
        }
        return DataTimeException().message.toString()
    }
}