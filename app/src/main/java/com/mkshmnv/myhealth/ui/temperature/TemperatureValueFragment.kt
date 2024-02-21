package com.mkshmnv.myhealth.ui.temperature

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.mkshmnv.myhealth.R
import com.mkshmnv.myhealth.databinding.FragmentTemperatureValueBinding
import com.mkshmnv.myhealth.ui.viewBinding
import java.time.LocalDate

class TemperatureValueFragment : Fragment(R.layout.fragment_temperature_value) {
    private val binding: FragmentTemperatureValueBinding by viewBinding()
    private val temperatureViewModel: TemperatureViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(view)

        binding.apply {
            temperatureViewModel.currentValue.value?.let {
                etDate.setText(it.date)
                etTime.setText(it.time)
                etValue.setText(it.value)
                chbPills.isChecked = it.pills
                etDescription.setText(it.description)
            }

            btnSave.setOnClickListener {
                temperatureViewModel.saveCurrentValue(
                    Temperature(
                        etDate.text.toString(),
                        etTime.text.toString(),
                        etValue.text.toString(),
                        chbPills.isChecked,
                        etDescription.text.toString()
                    )
                )
                navController.navigate(R.id.nav_temperature)
            }
        }
    }
}