package com.mkshmnv.myhealth.ui.temperature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class TemperatureViewModel : ViewModel() {

    private val _currentValue = MutableLiveData<Temperature>()
    val currentValue: LiveData<Temperature> = _currentValue

    private var _tempList = MutableLiveData<MutableList<Temperature>>()
    val tempList: MutableLiveData<MutableList<Temperature>> = _tempList

    fun getTemperatureList() {
        _tempList.value = mutableListOf(
            Temperature("2021-02-20", "21:00", "38.6", id = LocalDate.now())
        )
    }

    fun saveCurrentValue(currentValue: Temperature) {
        _currentValue.value = currentValue

    }

    init {
        _currentValue.observeForever {
            val position = _tempList.value?.indexOf(_currentValue.value)
//            _tempList.value?.removeAt(position!!)
            _currentValue.value?.let { temp -> _tempList.value!!.add(temp) }
        }
    }

    fun saveTemperature(temperature: Temperature) {
        val temp = _tempList.value!!.find { it.id == temperature.id }

        if (temp == null) {

        } else {
            val position = _tempList.value?.indexOf(temp)
            _tempList.value!![position!!] = temperature
        }
    }
}