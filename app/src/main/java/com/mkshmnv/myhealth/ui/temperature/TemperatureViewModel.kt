package com.mkshmnv.myhealth.ui.temperature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mkshmnv.myhealth.Logger
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TemperatureViewModel : ViewModel() {

    private val _currentTemperature = MutableLiveData<Temperature>()
    val currentTemperature: LiveData<Temperature> = _currentTemperature

    private var _tempList = MutableLiveData<MutableList<Temperature>>()
    val tempList: MutableLiveData<MutableList<Temperature>> = _tempList

    init {
        _tempList.value = mutableListOf(
            Temperature("2021-02-20", "21:00", "38.6", id = LocalDateTime.now()),
            Temperature("2021-02-21", "02:00", "36.6", id = LocalDateTime.of(2021, 2, 21, 2, 0)),
        )
    }

    fun saveChangedTemperature(temperature: Temperature) {
        Logger.logcat("tempList: ${tempList.value}")
        val index = _tempList.value?.indexOfFirst { it.id == temperature.id }
        if (index == -1 || index == null) {
            _tempList.value!!.add(temperature)
        } else {
            _tempList.value?.set(index, temperature)
        }
    }

    fun clickedTemperature(temperature: Temperature) {
        _currentTemperature.value = temperature
    }

    fun addNewTemperature() {
        val date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        val time = LocalDateTime.now().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))
        _currentTemperature.value = Temperature(date, time, "", id = LocalDateTime.now())
    }
}