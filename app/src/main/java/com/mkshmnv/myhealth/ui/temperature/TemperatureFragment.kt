package com.mkshmnv.myhealth.ui.temperature

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mkshmnv.myhealth.R
import com.mkshmnv.myhealth.databinding.FragmentTemperatureBinding
import com.mkshmnv.myhealth.db.TemperatureEntity
import com.mkshmnv.myhealth.ui.temperature.adapter.TemperatureAdapter
import com.mkshmnv.myhealth.ui.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TemperatureFragment : Fragment(R.layout.fragment_temperature),
    TemperatureAdapter.RecyclerViewEvent {

    private val binding: FragmentTemperatureBinding by viewBinding()
    private val temperatureViewModel: TemperatureViewModel by viewModels()

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        temperatureViewModel.getAllItems().observe(viewLifecycleOwner) { temperaturesList ->
            binding.apply {
                if (temperaturesList.isEmpty()) {
                    tvEmptyText.visibility = View.VISIBLE
                    rvTemperatureList.visibility = View.GONE
                } else {
                    rvTemperatureList.adapter =
                        TemperatureAdapter(temperaturesList, this@TemperatureFragment)
                    tvEmptyText.visibility = View.GONE
                    rvTemperatureList.visibility = View.VISIBLE
                }
            }
        }

        binding.fabTemperatureStats.setOnClickListener {
            Toast.makeText(context, "Temperature stats", Toast.LENGTH_SHORT).show()
        }

        binding.fabTemperatureAdd.setOnClickListener {
            navigateToDetailsWithItemId(0)
        }
    }

    override fun onItemClick(item: TemperatureEntity) {
        navigateToDetailsWithItemId(item.id)
    }

    private fun navigateToDetailsWithItemId(itemId: Int) {
        val action =
            TemperatureFragmentDirections.actionNavTemperatureToNavTemperatureDetails(itemId)
        navController.navigate(action)
    }
}