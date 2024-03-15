package com.mkshmnv.myhealth.ui.temperature.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkshmnv.myhealth.R
import com.mkshmnv.myhealth.databinding.ItemTemperatureBinding
import com.mkshmnv.myhealth.db.TemperatureEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TemperatureAdapter @Inject constructor(
    private val temperatureEntityList: List<TemperatureEntity>,
    private val listener: RecyclerViewEvent
) : RecyclerView.Adapter<TemperatureAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_temperature, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(temperatureEntityList[position])
    }

    override fun getItemCount() = temperatureEntityList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val binding = ItemTemperatureBinding.bind(view)

        private val temperatureValuesArray =
            view.context.resources.getStringArray(R.array.temperature_values)

        init {
            view.setOnClickListener(this)
        }

        @Suppress("DEPRECATION")
        fun bind(item: TemperatureEntity) {
            binding.apply {
                tvTemperatureDate.text = item.date
                tvTemperatureValue.text = temperatureValuesArray[item.value]
                tvTemperatureTime.text = item.time
                this@ViewHolder.itemView.context.resources.apply {
                    when (item.value) {
                        in 33..42 -> cvTemperatureItem.setCardBackgroundColor(getColor(R.color.light_green))
                        else -> cvTemperatureItem.setCardBackgroundColor(getColor(R.color.light_red))
                    }
                }
            }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(temperatureEntityList[position])
            }
        }
    }

    interface RecyclerViewEvent {
        fun onItemClick(item: TemperatureEntity)
    }
}