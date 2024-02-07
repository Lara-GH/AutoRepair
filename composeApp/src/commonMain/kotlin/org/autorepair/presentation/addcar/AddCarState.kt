package org.autorepair.presentation.addcar

data class AddCarState(
    val year: String,
    val manufacturer: String,
    val model: String,
    val engine: String,
    var show: Boolean
) {
    companion object {
        val Init = AddCarState(
            year = "",
            manufacturer = "",
            model = "",
            engine = "",
            show = false
        )
    }
}