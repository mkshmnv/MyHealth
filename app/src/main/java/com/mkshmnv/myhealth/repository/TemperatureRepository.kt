package com.mkshmnv.myhealth.repository

import androidx.lifecycle.LiveData
import com.mkshmnv.myhealth.db.TemperatureDao
import com.mkshmnv.myhealth.db.TemperatureEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TemperatureRepository @Inject constructor(private val dao: TemperatureDao) {
    fun insert(temperature: TemperatureEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.insert(temperature)
        }
    }

    fun update(temperature: TemperatureEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.update(temperature)
        }
    }

    fun delete(temperature: TemperatureEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.delete(temperature)
        }
    }

    fun getById(id: Int): TemperatureEntity = dao.getById(id)

    fun getAllItems(): LiveData<List<TemperatureEntity>> = dao.getAll()
}