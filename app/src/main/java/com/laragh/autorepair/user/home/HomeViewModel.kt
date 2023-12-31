package com.laragh.autorepair.user.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laragh.autorepair.UserRepository
import com.laragh.autorepair.user.models.Car

class HomeViewModel: ViewModel() {

    private val repository = UserRepository()

    private val mutableGetUserCarsLiveData: MutableLiveData<List<Car>> = MutableLiveData()
    val getUserCarsLiveData: LiveData<List<Car>> get() = mutableGetUserCarsLiveData

    private val mutableSelectedCar = MutableLiveData<Car>()
    val selectedCar: LiveData<Car> get() = mutableSelectedCar

    fun getUserCars() {
        repository.getUserCars(mutableGetUserCarsLiveData)
    }

    fun selectCar(car: Car) {
        mutableSelectedCar.value = car
    }

    fun addCar(car: Car){
        repository.addCar(car, mutableGetUserCarsLiveData)
    }
}