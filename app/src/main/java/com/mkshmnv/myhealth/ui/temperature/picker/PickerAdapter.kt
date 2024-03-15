package com.mkshmnv.myhealth.ui.temperature.picker

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mkshmnv.myhealth.R
import com.mkshmnv.myhealth.databinding.RowViewPickerItemBinding
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PickerAdapter @Inject constructor(
    private val items: Array<String>
) : RecyclerView.Adapter<PickerAdapter.ViewHolder>() {

    var callback: Callback? = null
    private val clickListener = View.OnClickListener { v -> v?.let { callback?.onItemClicked(it) } }
    private var selectedItem: Int? = -1
    private var ctx: Context? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RowViewPickerItemBinding.bind(view)
        val tvItem = binding.tvItem
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        ctx = viewGroup.context
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_view_picker_item, viewGroup, false)
        view.setOnClickListener(clickListener)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            tvItem.text = items[position]
            when (selectedItem) {
                in 33..42 -> tvItem.setTextColor(ContextCompat.getColor(ctx!!, R.color.green))
                else -> tvItem.setTextColor(ContextCompat.getColor(ctx!!, R.color.red))
            }
        }
    }

    override fun getItemCount() = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setSelectedItem(position: Int) {
        selectedItem = position
        notifyDataSetChanged()
    }

    fun getSelectedItem() = selectedItem!!

    interface Callback {
        fun onItemClicked(view: View)
    }
}