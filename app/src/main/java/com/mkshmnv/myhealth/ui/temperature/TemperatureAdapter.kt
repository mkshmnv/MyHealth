package com.mkshmnv.myhealth.ui.temperature

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mkshmnv.myhealth.Logger
import com.mkshmnv.myhealth.databinding.ItemTemperatureBinding
import com.mkshmnv.myhealth.db.TemperatureEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TemperatureAdapter @Inject constructor() :
    RecyclerView.Adapter<TemperatureAdapter.ViewHolder>() {
    private lateinit var binding: ItemTemperatureBinding
    private lateinit var context: Context

    override fun getItemCount() = differ.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemTemperatureBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: TemperatureEntity) {
            binding.apply {
                tvTemperatureDate.text = item.date
                tvTemperatureValue.text = item.value
                tvTemperatureTime.text = item.time

                ivTemperaturePills.apply {
                    if (item.pills) {
                        setImageResource(android.R.drawable.presence_online)
                    } else {
                        setImageResource(android.R.drawable.presence_offline)
                    }
                }

                root.setOnClickListener {
                    Logger.logcat("Temperature item clicked: $item")
                    val navController = Navigation.findNavController(itemView)
                    val action =
                        TemperatureFragmentDirections.actionNavTemperatureToNavTemperatureDetails(
                            item.id
                        )
                    navController.navigate(action)
                }
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<TemperatureEntity>() {
        override fun areItemsTheSame(
            oldItem: TemperatureEntity, newItem: TemperatureEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TemperatureEntity, newItem: TemperatureEntity
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}