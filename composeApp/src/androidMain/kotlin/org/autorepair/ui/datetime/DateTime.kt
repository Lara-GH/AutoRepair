package org.autorepair.ui.datetime

import android.os.Build
import androidx.annotation.RequiresApi
import org.autorepair.data.exceptions.DataTimeException
import org.autorepair.data.exceptions.IncorrectDataException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

actual object DateTime {
    @RequiresApi(Build.VERSION_CODES.O)
    @JvmStatic
    actual fun getFormattedDate(
    ): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss-SSS")
        try {
            return LocalDateTime.now().format(formatter)

        } catch (e: DataTimeException) {
            print(e.message)
        }
        return DataTimeException().message.toString()
    }
}