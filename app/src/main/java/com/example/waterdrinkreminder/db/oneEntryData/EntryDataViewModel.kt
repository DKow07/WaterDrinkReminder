package com.example.waterdrinkreminder.db.oneEntryData

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.waterdrinkreminder.db.historicaldata.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EntryDataViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: EntryDataRepository
    val allEntryData: LiveData<List<EntryDataEntity>>

    init {
        val entryDataDao = AppDatabase.getDatabase(application, viewModelScope).EntryDataDao()
        repository = EntryDataRepository(entryDataDao, "")
        allEntryData = repository.allEntryData
    }

    fun insert(entryDataEntity: EntryDataEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(entryDataEntity)
    }
}