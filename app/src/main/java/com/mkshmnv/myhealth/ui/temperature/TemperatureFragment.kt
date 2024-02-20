package com.mkshmnv.myhealth.ui.temperature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.mkshmnv.myhealth.R
import com.mkshmnv.myhealth.databinding.FragmentTemperatureBinding
import com.mkshmnv.myhealth.ui.viewBinding

class TemperatureFragment : Fragment(R.layout.fragment_temperature) {

    private val binding: FragmentTemperatureBinding by viewBinding()
    private val temperatureViewModel: TemperatureViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        temperatureViewModel.text.observe(viewLifecycleOwner, {

        })
    }
}