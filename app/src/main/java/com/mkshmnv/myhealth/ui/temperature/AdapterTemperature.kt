package com.mkshmnv.myhealth.ui.temperature

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkshmnv.myhealth.R
import com.mkshmnv.myhealth.databinding.ItemTemperatureBinding

data class Temperature(
    val date: String,
    val time: String,
    val data: String,
    val antipyretic: Boolean,
    val description: String
)

class AdapterTemperature : RecyclerView.Adapter<AdapterTemperature.TemperatureHolder>() {
    private val temperatureList = arrayListOf<Temperature>()

    class TemperatureHolder(brand: View) : RecyclerView.ViewHolder(brand) {
        private val binding = ItemTemperatureBinding.bind(brand)
        fun bind(temperature: Temperature) = binding.apply {
            tvTemperatureDate.text = temperature.date
            tvTemperatureTime.text = temperature.time
            tvTemperatureData.text = temperature.data
            ivTemperatureAntipyretic.visibility = if (temperature.antipyretic) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemperatureHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_temperature, parent, false)
        return TemperatureHolder(view)
    }

    override fun getItemCount(): Int {
        return temperatureList.size
    }

    override fun onBindViewHolder(holder: TemperatureHolder, position: Int) {
        holder.bind(temperatureList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAllBrands(list: List<Temperature>) {
        temperatureList.addAll(list)
        notifyDataSetChanged()
    }
}