package com.example.waterdrinkreminder.db.historicaldata

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoricalDataViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: HistoricalDataRepository
    val allHistoricalData: LiveData<List<HistoricalDataEntity>>

    init {
        val historicalDataDao = AppDatabase.getDatabase(application, viewModelScope).HistoricalDataDao()
        repository = HistoricalDataRepository(historicalDataDao)
        allHistoricalData = repository.allHistoricalData
    }

    fun insert(historicalDataEntity: HistoricalDataEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(historicalDataEntity)
    }
}