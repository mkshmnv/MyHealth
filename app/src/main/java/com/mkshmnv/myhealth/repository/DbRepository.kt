package com.mkshmnv.myhealth.repository

import com.mkshmnv.myhealth.db.TemperatureDao
import com.mkshmnv.myhealth.db.TemperatureEntity
import javax.inject.Inject

class DbRepository @Inject constructor(private val dao: TemperatureDao, ) {
    fun saveItem(temperature: TemperatureEntity) = dao.insert(temperature)
    fun updateItem(temperature: TemperatureEntity) = dao.update(temperature)
    fun deleteItem(temperature: TemperatureEntity) = dao.delete(temperature)
    fun getItemById(id : Int) : TemperatureEntity = dao.getById(id)
    fun getAllItems() = dao.getAll()
}