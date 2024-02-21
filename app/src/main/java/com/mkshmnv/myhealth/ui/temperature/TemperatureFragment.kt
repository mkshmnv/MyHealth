package com.mkshmnv.myhealth.ui.temperature

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.mkshmnv.myhealth.Logger
import com.mkshmnv.myhealth.R
import com.mkshmnv.myhealth.databinding.FragmentTemperatureBinding
import com.mkshmnv.myhealth.ui.viewBinding

class TemperatureFragment : Fragment(R.layout.fragment_temperature) {

    private val binding: FragmentTemperatureBinding by viewBinding()
    private val temperatureViewModel: TemperatureViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(view)
        val adapter = TemperatureAdapter {
            temperatureViewModel.clickedTemperature(it)
            navController.navigate(R.id.action_nav_temperature_to_nav_temperature_value)
        }

        binding.apply {
            rvTemperatureList.adapter = adapter

            btnTemperatureStats.setOnClickListener {
                Toast.makeText(context, "Temperature stats", Toast.LENGTH_SHORT).show()
//                temperatureViewModel.getTemperatureStats()
            }
            btnTemperatureAdd.setOnClickListener {
                temperatureViewModel.addNewTemperature()
                navController.navigate(R.id.action_nav_temperature_to_nav_temperature_value)
            }
        }

        temperatureViewModel.apply {
            tempList.observeForever {
                adapter.updateTemperatures(it)
            }
        }
    }
}