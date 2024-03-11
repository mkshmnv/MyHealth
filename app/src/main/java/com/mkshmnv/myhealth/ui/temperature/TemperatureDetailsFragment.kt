package com.mkshmnv.myhealth.ui.temperature

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.mkshmnv.myhealth.Logger
import com.mkshmnv.myhealth.R
import com.mkshmnv.myhealth.databinding.FragmentTemperatureDetailsBinding
import com.mkshmnv.myhealth.db.TemperatureEntity
import com.mkshmnv.myhealth.repository.DbRepository
import com.mkshmnv.myhealth.ui.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TemperatureDetailsFragment : Fragment(R.layout.fragment_temperature_details) {
    private val binding: FragmentTemperatureDetailsBinding by viewBinding()

    @Inject
    lateinit var repository: DbRepository

    @Inject
    lateinit var temperature: TemperatureEntity
    private var temperatureId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: TemperatureDetailsFragmentArgs by navArgs()
        Logger.logcat("TemperatureDetailsFragment: args.id = ${args.id}")
        temperatureId = args.id
        if (temperatureId != 0) {
            // Load item from database if exists
            try {
                temperature = repository.getItemById(args.id)
                temperatureId = temperature.id
                binding.apply {
                    etDate.setText(temperature.date)
                    etTime.setText(temperature.time)
                    etValue.setText(temperature.value)
                    chbPills.isChecked = temperature.pills
                    etDescription.setText(temperature.description)
                    fabDelete.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                Snackbar.make(view, "Item not found", Snackbar.LENGTH_LONG).show()
            }
        }

        binding.apply {
            fabDelete.setOnClickListener {
                setValuesFromUI()
                repository.deleteItem(temperature)

                backToTemperatureFragment()
            }

            fabSave.setOnClickListener {
                setValuesFromUI()
                repository.saveItem(temperature)

                backToTemperatureFragment()
            }
        }
    }

    private fun backToTemperatureFragment() {
        val navController = this.view?.let { Navigation.findNavController(it) }
        val action =
            TemperatureDetailsFragmentDirections.actionNavTemperatureDetailsToNavTemperature()
        navController?.navigate(action)
    }

    private fun setValuesFromUI() {
        binding.apply {
            val date = etDate.text.toString()
            val time = etTime.text.toString()
            val value = etValue.text.toString()
            val pills = chbPills.isChecked
            val desc = etDescription.text.toString()
            if (date.isEmpty() || time.isEmpty() || value.isEmpty()) {
                view?.let {
                    Snackbar.make(it, "Date, Time or Value cannot be Empty", Snackbar.LENGTH_LONG)
                        .show()
                    return
                }
            }

            temperature = TemperatureEntity(
                id = temperatureId,
                date = date,
                time = time,
                value = value,
                pills = pills,
                description = desc
            )
        }
    }
}