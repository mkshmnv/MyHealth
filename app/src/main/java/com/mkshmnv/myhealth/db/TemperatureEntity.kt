package com.mkshmnv.myhealth.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mkshmnv.myhealth.utils.Constants.TEMPERATURE_TABLE

@Entity(tableName = TEMPERATURE_TABLE)
data class TemperatureEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "temperature_date")
    val date: String = "",
    @ColumnInfo(name = "temperature_time")
    val time: String = "",
    @ColumnInfo(name = "temperature_value")
    val value: String = "",
    @ColumnInfo(name = "temperature_pills")
    val pills: Boolean = false,
    @ColumnInfo(name = "temperature_desc")
    val description: String = ""
)