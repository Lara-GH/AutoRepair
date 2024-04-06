package org.autorepair.ui.datetime

expect object DateTime {
    fun getCurrentDateTime(): String
    fun getFormattedDateTime(inputString: String): String
}