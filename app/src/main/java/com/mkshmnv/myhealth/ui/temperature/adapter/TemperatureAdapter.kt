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
    private val items: List<TemperatureEntity>,
    private val listener: RecyclerViewEvent
) : RecyclerView.Adapter<TemperatureAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_temperature, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val binding = ItemTemperatureBinding.bind(view)

        init {
            view.setOnClickListener(this)
        }

        fun bind(item: TemperatureEntity) {
            binding.apply {
                tvTemperatureDate.text = item.date
                tvTemperatureValue.text = item.value
                tvTemperatureTime.text = item.time
            }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(items[position])
            }
        }
    }

    interface RecyclerViewEvent {
        fun onItemClick(item: TemperatureEntity)
    }
}