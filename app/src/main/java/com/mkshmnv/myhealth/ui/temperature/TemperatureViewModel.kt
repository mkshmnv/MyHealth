package com.mkshmnv.myhealth.ui.temperature

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mkshmnv.myhealth.Logger
import com.mkshmnv.myhealth.db.TemperatureEntity
import com.mkshmnv.myhealth.repository.TemperatureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TemperatureViewModel @Inject constructor(
    private val repository: TemperatureRepository
) : ViewModel() {

    fun addItem(item: TemperatureEntity) {
        return repository.insert(item)
    }

    fun deleteItemById(id: Int) {
        repository.deleteById(id)
    }

    fun updateItem(item: TemperatureEntity) {
        Logger.logcat("update is called in viewModel")
        repository.update(item)
    }

    fun getItemById(id: Int) = repository.getById(id)

    fun getAllItems(): LiveData<List<TemperatureEntity>> = repository.getAllItems()
}