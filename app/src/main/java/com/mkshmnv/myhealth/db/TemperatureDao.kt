package com.mkshmnv.myhealth.db

import androidx.lifecycle.LiveData
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
    fun insert(temperatureNote: TemperatureEntity)

    @Update
    fun update(temperatureNote: TemperatureEntity)

    @Delete
    fun delete(temperatureNote: TemperatureEntity)

    @Query("SELECT * FROM $TEMPERATURE_TABLE ORDER BY id DESC")
    fun getAll(): LiveData<List<TemperatureEntity>>

    @Query("SELECT * FROM $TEMPERATURE_TABLE WHERE id LIKE :id")
    fun getById(id: Int): TemperatureEntity
}