package com.mkshmnv.myhealth.ui.temperature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private var _currentTemperature = MutableLiveData<TemperatureEntity>()
    val currentTemperature: LiveData<TemperatureEntity> = _currentTemperature

    fun addItem(item: TemperatureEntity) {
        return repository.insert(item)
    }

    fun deleteItem(item: TemperatureEntity) {
        repository.delete(item)
    }

    fun deleteItemById(id: Int) {
        repository.deleteById(id)
    }

    fun updateItem(item: TemperatureEntity) {
        Logger.logcat("update is called in viewModel")
        repository.update(item)
    }

    fun setItemById(id: Int) {
        try {
            _currentTemperature.postValue(repository.getById(id))
        } catch (e: Exception) {
            Logger.logcat("Item not found")
        }
    }

    fun getAllItems(): LiveData<List<TemperatureEntity>> {
        Logger.logcat("View model getAllItems")
        return repository.getAllItems()
    }
}