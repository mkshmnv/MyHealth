package com.mkshmnv.myhealth.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TemperatureEntity::class], version = 1)
abstract class TemperatureDatabase : RoomDatabase() {
    abstract fun temperatureDoa(): TemperatureDao
}