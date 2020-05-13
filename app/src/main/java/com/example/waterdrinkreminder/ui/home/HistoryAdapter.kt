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

class HistoryAdapter(val items: ArrayList<HistoricalDataEntity>, val context: Context) : RecyclerView.Adapter<HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.history_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.date?.text = items[position].date
        holder.percentageVolume?.text = items[position].percentageVolume.toString() + "%"
        holder.volume?.text = items[position].volume
    }

    fun updateData(data: ArrayList<HistoricalDataEntity>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }
}

class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val date = view.historyItemDate
    val percentageVolume = view.historyItemPercentageVolume
    val volume = view.historyItemVolume
}