package com.mkshmnv.myhealth.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mkshmnv.myhealth.utils.Constants.TEMPERATURE_TABLE

@Dao
interface TemperatureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(temperatureEntity: TemperatureEntity)

    @Update
    fun update(temperatureEntity: TemperatureEntity)

    @Delete
    fun delete(temperatureEntity: TemperatureEntity)

    @Query("SELECT * FROM $TEMPERATURE_TABLE ORDER BY id DESC")
    fun getAll(): MutableList<TemperatureEntity>

    @Query("SELECT * FROM $TEMPERATURE_TABLE WHERE id LIKE :id")
    fun getById(id: Int): TemperatureEntity
}