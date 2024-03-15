package com.mkshmnv.myhealth.di

import android.content.Context
import androidx.room.Room
import com.mkshmnv.myhealth.db.TemperatureDatabase
import com.mkshmnv.myhealth.db.TemperatureEntity
import com.mkshmnv.myhealth.ui.temperature.picker.PickerLayoutManager
import com.mkshmnv.myhealth.utils.Constants.TEMPERATURE_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.Calendar
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, TemperatureDatabase::class.java, TEMPERATURE_DATABASE
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(db: TemperatureDatabase) = db.temperatureDoa()

    @Provides
    fun provideEntity() = TemperatureEntity()

    @Provides
    @Singleton
    fun providePickerLayoutManager(@ApplicationContext context: Context) =
        PickerLayoutManager(context)

    @Provides
    @Singleton
    fun provideCalendar(): Calendar = Calendar.getInstance()
}