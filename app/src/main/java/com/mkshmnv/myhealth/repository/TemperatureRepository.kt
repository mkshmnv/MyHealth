package com.mkshmnv.myhealth.repository

import androidx.lifecycle.LiveData
import com.mkshmnv.myhealth.db.TemperatureDao
import com.mkshmnv.myhealth.db.TemperatureEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TemperatureRepository @Inject constructor(private val dao: TemperatureDao, ) {
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

    fun deleteById(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteById(id)
        }
    }

    fun getAllItems(): LiveData<List<TemperatureEntity>> {
        return dao.getAll()
    }

    fun getById(id: Int): TemperatureEntity {
        return dao.getById(id)
    }
}