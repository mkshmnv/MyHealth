package com.mkshmnv.myhealth.ui.temperature

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mkshmnv.myhealth.R
import com.mkshmnv.myhealth.databinding.FragmentTemperatureBinding
import com.mkshmnv.myhealth.db.TemperatureEntity
import com.mkshmnv.myhealth.ui.temperature.adapter.TemperatureAdapter
import com.mkshmnv.myhealth.ui.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TemperatureFragment : Fragment(R.layout.fragment_temperature),
    TemperatureAdapter.RecyclerViewEvent {

    private val binding: FragmentTemperatureBinding by viewBinding()
    private val temperatureViewModel: TemperatureViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        temperatureViewModel.getAllItems().observe(viewLifecycleOwner) { temperaturesList ->
            binding.apply {
                if (temperaturesList.isEmpty()) {
                    tvEmptyText.visibility = View.VISIBLE
                    rvTemperatureList.visibility = View.GONE
                } else {
                    val adapter = TemperatureAdapter(temperaturesList, this@TemperatureFragment)
                    rvTemperatureList.adapter = adapter
                    rvTemperatureList.visibility = View.VISIBLE
                    tvEmptyText.visibility = View.GONE
                }
            }
        }

        binding.apply {
            fabTemperatureStats.setOnClickListener {
                Toast.makeText(context, "Temperature stats", Toast.LENGTH_SHORT).show()
            }
            fabTemperatureAdd.setOnClickListener {
                navigateToDetailsItemWithId(0)
            }
        }
    }

    override fun onItemClick(item: TemperatureEntity) {
        navigateToDetailsItemWithId(item.id)
    }

    private fun navigateToDetailsItemWithId(itemId: Int) {
        val action =
            TemperatureFragmentDirections.actionNavTemperatureToNavTemperatureDetails()

        temperatureViewModel.initCurrentItem(itemId)
        // TODO: impl waiting animation
        temperatureViewModel.temperature.observe(viewLifecycleOwner) {
            val navController: NavController = Navigation.findNavController(requireView())
            navController.navigate(action)
        }
    }
}