package com.example.waterdrinkreminder.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.waterdrinkreminder.R
import com.example.waterdrinkreminder.db.HistoricalDataEntity
import com.example.waterdrinkreminder.model.HistoricalData
import kotlinx.android.synthetic.main.history_item.view.*

class HistoricalDataAdapter() : RecyclerView.Adapter<HistoricalDataViewHolder>() {

    private val items = mutableListOf<HistoricalDataEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricalDataViewHolder {
        return HistoricalDataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: HistoricalDataViewHolder, position: Int) {
        if(itemCount > position) {
            holder.date?.text = items[position].date
            holder.percentageVolume?.text = items[position].percentageVolume.toString() + "%"
            holder.volume?.text = items[position].volume
        }
    }

    fun updateData(data: ArrayList<HistoricalDataEntity>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }
}

class HistoricalDataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val date = view.historyItemDate
    val percentageVolume = view.historyItemPercentageVolume
    val volume = view.historyItemVolume
}