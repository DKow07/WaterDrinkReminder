package com.example.waterdrinkreminder.ui.home

import com.example.waterdrinkreminder.db.historicaldata.HistoricalDataEntity

interface HistoricalDataListener {
    fun onHistoricalDataClickListener(historicalDataEntity: HistoricalDataEntity)
}