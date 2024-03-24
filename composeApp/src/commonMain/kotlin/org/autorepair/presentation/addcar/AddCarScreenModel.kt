package org.autorepair.presentation.addcar

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import org.autorepair.data.models.Manufacturer
import org.autorepair.data.models.Year
import org.autorepair.domain.models.YearManufacturers
import org.autorepair.domain.repository.CarRepository

class AddCarScreenModel(
    private val carRepository: CarRepository
) : StateScreenModel<AddCarState>(AddCarState.Init) {
    private val _carHierarchyState = mutableStateOf<YearManufacturers?>(null)
    val carHierarchyState: State<YearManufacturers?> = _carHierarchyState

    private val _selectedYear = mutableStateOf<String?>(null)
    val selectedYear: State<String?> = _selectedYear

    private val _selectedManufacturer = mutableStateOf<String?>(null)
    val selectedManufacturer: State<String?> = _selectedManufacturer

    private val _selectedModel = mutableStateOf<String?>(null)
    val selectedModel: State<String?> = _selectedModel

    private val _selectedEngine = mutableStateOf<String?>(null)
    val selectedEngine: State<String?> = _selectedEngine

    init {
        getCarsList()
    }

    private fun getCarsList() {
        screenModelScope.launch {
            _carHierarchyState.value = carRepository.getCarHierarchy().getOrNull()
        }
    }

    fun getYearsList(): List<String> {
        return carHierarchyState.value?.yearToManufacturerMap
            ?.map { it.key.value }?.sorted()
            ?: emptyList()
    }

    fun onYearSelected(year: String) {
        _selectedYear.value = year

        screenModelScope.launch {
            carRepository.selectCar(year)
        }
    }

    fun getManufacturersList(): List<String> {

        screenModelScope.launch {
            val id = carRepository.getSelectedCarId().getOrNull()
            println("id = $id")
        }

        val year: String = selectedYear.value ?: ""

        return carHierarchyState.value?.yearToManufacturerMap
            ?.get(key = Year(year))
            ?.map { it.manufacturer.name }?.sorted()
            ?: emptyList()
    }

    fun onManufacturerSelected(manufacturer: String) {
        _selectedManufacturer.value = manufacturer
    }

    fun getModelsList(): List<String> {
        val year: String = selectedYear.value ?: ""
        val manufacturer: String = selectedManufacturer.value ?: ""

        return carHierarchyState.value?.yearToManufacturerMap
            ?.get(key = Year(year))
            ?.find { it.manufacturer == Manufacturer(manufacturer) }
            ?.models?.map { it.model }?.sorted()
            ?: emptyList()
    }

    fun onModelSelected(model: String) {
        _selectedModel.value = model
    }

    fun getEnginesList(): List<String> {
        val year: String = selectedYear.value ?: ""
        val manufacturer: String = selectedManufacturer.value ?: ""
        val model: String = selectedModel.value ?: ""

        return carHierarchyState.value?.yearToManufacturerMap
            ?.get(key = Year(year))
            ?.find { it.manufacturer == Manufacturer(manufacturer) }
            ?.models
            ?.find { it.model == model }
            ?.engines
            ?.map { it.name }?.sorted()
            ?: emptyList()
    }

    fun onEngineSelected(engine: String) {
        _selectedEngine.value = engine
    }

}