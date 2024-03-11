package com.mkshmnv.myhealth.ui.temperature

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.mkshmnv.myhealth.R
import com.mkshmnv.myhealth.databinding.FragmentTemperatureBinding
import com.mkshmnv.myhealth.db.TemperatureEntity
import com.mkshmnv.myhealth.repository.DbRepository
import com.mkshmnv.myhealth.ui.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TemperatureFragment : Fragment(R.layout.fragment_temperature) {
    private val binding: FragmentTemperatureBinding by viewBinding()

    @Inject
    lateinit var repository: DbRepository

    @Inject
    lateinit var temperature: TemperatureEntity

    @Inject
    lateinit var adapter: TemperatureAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkItem()
        binding.apply {
            fabTemperatureStats.setOnClickListener {
                Toast.makeText(context, "Temperature stats", Toast.LENGTH_SHORT).show()
            }

            fabTemperatureAdd.setOnClickListener {
                val navController = Navigation.findNavController(view)
                val action =
                    TemperatureFragmentDirections.actionNavTemperatureToNavTemperatureDetails(0)
                navController.navigate(action)
//                checkItem()
            }
        }
    }

//    override fun onResume() {
//        super.onResume()
//        checkItem()
//    }

    private fun checkItem() {
        val allItems = repository.getAllItems()
        binding.apply {
            if (allItems.isNotEmpty()) {
                rvTemperatureList.visibility = View.VISIBLE
                tvEmptyText.visibility = View.GONE
                adapter.differ.submitList(allItems)
                rvTemperatureList.adapter = adapter
            } else {
                rvTemperatureList.visibility = View.GONE
                tvEmptyText.visibility = View.VISIBLE
            }
        }
    }
}