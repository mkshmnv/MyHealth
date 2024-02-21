package com.mkshmnv.myhealth.ui.temperature

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkshmnv.myhealth.R
import com.mkshmnv.myhealth.databinding.ItemTemperatureBinding
import java.time.LocalDate

data class Temperature(
    val date: String,
    val time: String,
    val value: String,
    val pills: Boolean = false,
    val description: String = "",
    val id: LocalDate = LocalDate.now()
)

class TemperatureAdapter(private val onItemClick: (Temperature) -> Unit) :
    RecyclerView.Adapter<TemperatureAdapter.TemperatureViewHolder>() {
    private var temperatureList: List<Temperature> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun getItemCount() = temperatureList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemperatureViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_temperature, parent, false)
        return TemperatureViewHolder(view)
    }

    class TemperatureViewHolder(temperature: View) : RecyclerView.ViewHolder(temperature) {
        private val binding = ItemTemperatureBinding.bind(temperature)
        fun bind(temperature: Temperature) = binding.apply {
            tvTemperatureDate.text = temperature.date
            tvTemperatureTime.text = temperature.time
            tvTemperatureValue.text = temperature.value
        }
    }


    override fun onBindViewHolder(holder: TemperatureViewHolder, position: Int) {
        holder.apply {
            val item = temperatureList[position]
            bind(item)
            itemView.setOnClickListener { onItemClick(item) }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateTemperatures(list: List<Temperature>) {
        temperatureList.addAll(list)
        notifyDataSetChanged()
    }
}