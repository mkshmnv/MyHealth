package com.mkshmnv.myhealth.ui.temperature

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.mkshmnv.myhealth.R
import com.mkshmnv.myhealth.databinding.FragmentTemperatureDetailsBinding
import com.mkshmnv.myhealth.ui.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class TemperatureDetailsFragment : Fragment(R.layout.fragment_temperature_details) {
    private val binding: FragmentTemperatureDetailsBinding by viewBinding()
    private val temperatureViewModel: TemperatureViewModel by activityViewModels()

    @Inject
    lateinit var calendar: Calendar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            // Set values to UI from the ViewModel
            temperatureViewModel.temperature.value?.let { temperature ->
                tvDate.text = temperature.date
                tvTime.text = temperature.time
                pickerValue.setTemperatures(temperature.value)
                chbPills.isChecked = temperature.pills
                etDescription.setText(temperature.description)

                if (temperature.id == 0) fabDelete.hide() else fabDelete.show()
            }

            // Set listeners
            tvDate.setOnClickListener {
                showDatePickerDialog()
            }

            tvTime.setOnClickListener {
                showTimePickerDialog()
            }

            fabSave.setOnClickListener {
                temperatureViewModel.saveItem(
                    date = tvDate.text.toString(),
                    time = tvTime.text.toString(),
                    value = pickerValue.value,
                    pills = chbPills.isChecked,
                    description = etDescription.text.toString()
                )
                backToTemperatureFragment()
            }

            fabDelete.setOnClickListener {
                temperatureViewModel.deleteItem()
                backToTemperatureFragment()
            }
        }
    }

    // Show date picker dialog and set date to TextView
    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            requireActivity(),
            { _, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                calendar.set(year, monthOfYear, dayOfMonth)
                val dateFormat =
                    SimpleDateFormat(getString(R.string.format_date), Locale.getDefault())
                val formattedDate = dateFormat.format(calendar.time)
                binding.tvDate.text = formattedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    // Show time picker dialog and set time to TextView
    private fun showTimePickerDialog() {
        val timePickerDialog = TimePickerDialog(
            requireActivity(), { _, hourOfDay, minute ->
                binding.tvTime.text = String.format("%d:%d", hourOfDay, minute)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        timePickerDialog.show()
    }

    // Ext set temperatures values to NumberPicker
    @Suppress("DEPRECATION")
    private fun NumberPicker.setTemperatures(indexValue: Int) {
        val values = resources.getStringArray(R.array.temperature_values)
        val normalValues = 33..40
        val greenColor = resources.getColor(R.color.green)
        val redColor = resources.getColor(R.color.red)
        this.minValue = 0
        this.maxValue = values.size - 1
        this.displayedValues = values
        this.textColor = if (indexValue in normalValues) greenColor else redColor
        this.value = indexValue
        this.setOnValueChangedListener { _, _, newVal ->
            this.textColor = if (newVal in normalValues) greenColor else redColor
        }
    }

    // Navigate back to the TemperatureFragment
    private fun backToTemperatureFragment() {
        // Navigate back to the TemperatureFragment
        val navController = this.view?.let { Navigation.findNavController(it) }
        val action =
            TemperatureDetailsFragmentDirections.actionNavTemperatureDetailsToNavTemperature()
        navController?.navigate(action)
    }
}