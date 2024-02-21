package com.mkshmnv.myhealth.ui.temperature

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.mkshmnv.myhealth.Logger
import com.mkshmnv.myhealth.R
import com.mkshmnv.myhealth.databinding.FragmentTemperatureValueBinding
import com.mkshmnv.myhealth.ui.viewBinding
import java.util.Calendar

class TemperatureValueFragment : Fragment(R.layout.fragment_temperature_value) {
    private val binding: FragmentTemperatureValueBinding by viewBinding()
    private val temperatureViewModel: TemperatureViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(view)

        temperatureViewModel.currentTemperature.value?.let { temperature ->
            binding.apply {
                etDate.setText(temperature.date)
                etTime.setText(temperature.time)
                etValue.setText(temperature.value)
                chbPills.isChecked = temperature.pills
                etDescription.setText(temperature.description)

                btnSave.setOnClickListener {
                    val newValue = Temperature(
                        date = etDate.text.toString(),
                        time = etTime.text.toString(),
                        value = etValue.text.toString(),
                        pills = chbPills.isChecked,
                        description = etDescription.text.toString(),
                        id = temperature.id
                    )
                    Logger.logcat("Temperature saved: $newValue")
                    temperatureViewModel.saveChangedTemperature(newValue)
                    navController.navigate(R.id.nav_temperature)
                }

                etDate.apply {
                    showSoftInputOnFocus = false
                    setOnClickListener {
                        showDatePickerDialog()
                    }
                }
            }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val month = if (monthOfYear < 9) "0${monthOfYear + 1}" else "${monthOfYear + 1}"
                val day = if (dayOfMonth < 9) "0$dayOfMonth" else "$dayOfMonth"
                val selectedDate = "$day.$month.$year"
                binding.etDate.setText(selectedDate)
            },
            year,
            month,
            dayOfMonth
        )
        datePickerDialog.show()
    }
}