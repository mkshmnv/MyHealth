package com.mkshmnv.myhealth.di

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Room
import com.mkshmnv.myhealth.R
import com.mkshmnv.myhealth.db.TemperatureDatabase
import com.mkshmnv.myhealth.db.TemperatureEntity
import com.mkshmnv.myhealth.utils.Constants.TEMPERATURE_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
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

    @SuppressLint("SimpleDateFormat")
    @Provides
    fun provideEntity(@ApplicationContext context: Context) = TemperatureEntity(
        date = SimpleDateFormat(context.getString(R.string.format_date)).format(Date()),
        time = SimpleDateFormat(context.getString(R.string.format_time)).format(Date())
    )

    @Provides
    @Singleton
    fun provideCalendar(): Calendar = Calendar.getInstance()
}