package com.example.waterdrinkreminder.db.historicaldata

import androidx.lifecycle.LiveData

class HistoricalDataRepository(private val historicalDataDao: HistoricalDataDao) {

    val allHistoricalData: LiveData<List<HistoricalDataEntity>> = historicalDataDao.getAll()

    suspend fun insert(historicalDataEntity: HistoricalDataEntity) {
        historicalDataDao.insert(historicalDataEntity)
    }
}