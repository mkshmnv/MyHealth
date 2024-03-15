package com.mkshmnv.myhealth.ui.temperature

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.mkshmnv.myhealth.Logger
import com.mkshmnv.myhealth.R
import com.mkshmnv.myhealth.databinding.FragmentTemperatureDetailsBinding
import com.mkshmnv.myhealth.db.TemperatureEntity
import com.mkshmnv.myhealth.ui.temperature.picker.PickerAdapter
import com.mkshmnv.myhealth.ui.temperature.picker.PickerLayoutManager
import com.mkshmnv.myhealth.ui.temperature.picker.ScreenUtils
import com.mkshmnv.myhealth.ui.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
@Singleton
class TemperatureDetailsFragment : Fragment(R.layout.fragment_temperature_details) {

    private val binding: FragmentTemperatureDetailsBinding by viewBinding()
    private val temperatureViewModel: TemperatureViewModel by viewModels()

    @Inject
    lateinit var pickerLayoutManager: PickerLayoutManager

    @Inject
    lateinit var temperature: TemperatureEntity

    // TODO: impl HILT
    lateinit var pickerAdapter: PickerAdapter

    @Inject
    lateinit var calendar: Calendar

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the temperature picker (recyclerview)
        setTemperaturePicker()

        // Get the argument item ID from TemperatureFragment on item click
        val args: TemperatureDetailsFragmentArgs by navArgs()

        if (args.id != 0) {
            // If item an existing we need to get it from the database
            temperatureViewModel.getItemById(args.id).let {
                temperature = it
                binding.apply {
                    // and set the values to the UI elements
                    tvDate.text = temperature.date
                    tvTime.text = temperature.time
                    pickerTemperatures.post {
                        pickerTemperatures.smoothScrollToPosition(temperature.value)
                    }
                    chbPills.isChecked = temperature.pills
                    etDescription.setText(temperature.description)

                    // Show the delete button if the item is not new and we can delete it
                    fabDelete.visibility = View.VISIBLE
                }
            }
        } else {
            binding.apply {
                // If item is new, we need to set the default values
                tvDate.text = SimpleDateFormat(getString(R.string.format_date)).format(Date())
                tvTime.text = SimpleDateFormat(getString(R.string.format_time)).format(Date())
                pickerTemperatures.post {
                    pickerTemperatures.smoothScrollToPosition(36)
                }

                // Hide the delete button if the item is new
                fabDelete.visibility = View.GONE
            }
        }

        binding.apply {
            tvDate.setOnClickListener {
                showDatePickerDialog()
            }

            tvTime.setOnClickListener {
                showTimePickerDialog()
            }

            fabDelete.setOnClickListener {
                temperatureViewModel.deleteItemById(args.id)
                backToTemperatureFragment()
            }

            fabSave.setOnClickListener {
                if (temperature.value != -1) {
                    valuesFromUI()
                    if (args.id != 0) {
                        temperatureViewModel.updateItem(temperature)
                    } else {
                        temperatureViewModel.addItem(temperature)
                    }
                    backToTemperatureFragment()
                }
            }
        }
    }

    private fun showDatePickerDialog() {
        // Show date picker dialog and set date to TextView
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

    private fun setTemperaturePicker() {
        val padding = ScreenUtils.getScreenWidth(requireActivity()) / 2 - ScreenUtils.dpToPx(
            requireActivity(),
            40
        )

        val items: Array<String> = resources.getStringArray(R.array.temperature_values)

        binding.pickerTemperatures.apply {
            // Setting the padding such that the items will appear in the middle of the screen
            setPadding(padding, 0, padding, 0)
            // Setting layout manager
            layoutManager = pickerLayoutManager.apply {
                callback = object : PickerLayoutManager.OnItemSelectedListener {
                    override fun onItemSelected(layoutPosition: Int) {
                        Logger.logcat("Position = ${items[layoutPosition]}")
                        pickerAdapter.setSelectedItem(layoutPosition)
                    }
                }
            }
        }

        // Setting Adapter
        pickerAdapter = PickerAdapter(items)
        binding.apply {
            pickerTemperatures.adapter = pickerAdapter.apply {
                callback = object : PickerAdapter.Callback {
                    override fun onItemClicked(view: View) {
                        pickerTemperatures.smoothScrollToPosition(
                            pickerTemperatures.getChildLayoutPosition(view)
                        )
                    }
                }
            }
        }
    }

    private fun valuesFromUI() {
        binding.apply {
            temperature.date = tvDate.text.toString()
            temperature.time = tvTime.text.toString()
            temperature.value = pickerAdapter.getSelectedItem()
            temperature.pills = chbPills.isChecked
            temperature.description = etDescription.text.toString()
        }
    }

    private fun backToTemperatureFragment() {
        binding.pickerTemperatures.layoutManager = null
        val navController = this.view?.let { Navigation.findNavController(it) }
        val action =
            TemperatureDetailsFragmentDirections.actionNavTemperatureDetailsToNavTemperature()
        navController?.navigate(action)
    }
}