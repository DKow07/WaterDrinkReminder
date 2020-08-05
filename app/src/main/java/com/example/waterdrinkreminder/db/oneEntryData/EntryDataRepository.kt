package com.example.waterdrinkreminder.db.oneEntryData

import androidx.lifecycle.LiveData

class EntryDataRepository(private val entryDataDao: EntryDataDao, private val currDate: String) {

    val allEntryData: LiveData<List<EntryDataEntity>> = entryDataDao.getAll(currDate)

    suspend fun insert(entryDataEntity: EntryDataEntity) {
        entryDataDao.insert(entryDataEntity)
    }
}