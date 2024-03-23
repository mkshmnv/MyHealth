package com.mkshmnv.myhealth.ui.temperature

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mkshmnv.myhealth.Logger
import com.mkshmnv.myhealth.db.TemperatureEntity
import com.mkshmnv.myhealth.repository.TemperatureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TemperatureViewModel @Inject constructor(
    private val repository: TemperatureRepository
) : ViewModel() {
    // LiveData
    private var _temperature = MutableLiveData<TemperatureEntity>()
    val temperature: LiveData<TemperatureEntity> = _temperature

    //
    fun getAllItems(): LiveData<List<TemperatureEntity>> = repository.getAllItems()

    @SuppressLint("SimpleDateFormat")
    fun setCurrentItem(id: Int) {
        _temperature.value = if (id == 0) {
            TemperatureEntity(
                date = SimpleDateFormat("dd.MM.yyyy").format(Date()),
                time = SimpleDateFormat("HH:mm").format(Date())
            )
        } else {
            repository.getById(id)
        }
    }

    fun saveItem(date: String, time: String, value: Int, pills: Boolean, description: String) {
        _temperature.value?.apply {
            this.date = date
            this.time = time
            this.value = value
            this.pills = pills
            this.description = description
            if (this.id == 0) repository.insert(this) else repository.update(this)
        }
    }

    fun deleteItem() {
        _temperature.value?.let { repository.delete(it) }
    }
}