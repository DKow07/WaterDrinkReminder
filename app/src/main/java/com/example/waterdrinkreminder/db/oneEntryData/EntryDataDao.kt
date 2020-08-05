package com.example.waterdrinkreminder.db.oneEntryData

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EntryDataDao {

    @Query("SELECT * from entry_data WHERE date=:currDate")
    fun getAll(currDate: String): LiveData<List<EntryDataEntity>>

    @Insert
    suspend fun insert(data: EntryDataEntity)

    @Query("DELETE FROM entry_data")
    suspend fun deleteAll()
}