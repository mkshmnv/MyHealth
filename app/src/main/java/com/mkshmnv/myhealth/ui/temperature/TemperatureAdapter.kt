package com.mkshmnv.myhealth.ui.temperature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkshmnv.myhealth.Logger
import com.mkshmnv.myhealth.databinding.ItemTemperatureBinding
import java.time.LocalDateTime

data class Temperature(
    val date: String,
    val time: String,
    val value: String,
    val pills: Boolean = false,
    val description: String = "",
    val id: LocalDateTime = LocalDateTime.now()
)

class TemperatureAdapter(private val onItemClick: (Temperature) -> Unit) :
    RecyclerView.Adapter<TemperatureAdapter.TemperatureViewHolder>() {
    private var temperaturesList: MutableList<Temperature> = mutableListOf()

    override fun getItemCount() = temperaturesList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemperatureViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTemperatureBinding.inflate(inflater, parent, false)
        return TemperatureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TemperatureViewHolder, position: Int) {
        val temperature = temperaturesList[position]
        holder.binding.apply {
            tvTemperatureDate.text = temperature.date
            tvTemperatureTime.text = temperature.time
            tvTemperatureValue.text = temperature.value
        }
        holder.itemView.setOnClickListener {
            onItemClick(temperature)
        }
    }

    class TemperatureViewHolder(val binding: ItemTemperatureBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun updateTemperatures(list: List<Temperature>) {
        Logger.logcat("updateTemperatures list: $list", "TemperatureAdapter")
        temperaturesList.clear()
        temperaturesList.addAll(list.toMutableList())
        Logger.logcat(
            "updateTemperatures temperaturesList: $temperaturesList",
            "TemperatureAdapter"
        )
        notifyDataSetChanged()
    }
}