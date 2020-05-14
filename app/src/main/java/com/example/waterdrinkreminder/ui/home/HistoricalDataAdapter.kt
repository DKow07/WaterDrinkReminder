package com.example.waterdrinkreminder.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.waterdrinkreminder.R
import com.example.waterdrinkreminder.db.historicaldata.HistoricalDataEntity
import kotlinx.android.synthetic.main.historical_item.view.*

class HistoricalDataAdapter() : RecyclerView.Adapter<HistoricalDataViewHolder>() {

    private val items = mutableListOf<HistoricalDataEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricalDataViewHolder {
        return HistoricalDataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.historical_item, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: HistoricalDataViewHolder, position: Int) {
        if(itemCount > position) {
            holder.bindData(items[position])
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

    fun bindData(data: HistoricalDataEntity) {
        date?.text = data.date
        volume?.text = data.remainingVolume.toString() + "ml/" + data.targetVolume.toString() + "ml"
        var percentageVolumeFloat: Int = ((data.remainingVolume.toFloat() / data.targetVolume.toFloat()) * 100).toInt()
        percentageVolume?.text = percentageVolumeFloat.toString() + "%"
    }
}