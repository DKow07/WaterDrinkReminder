package com.example.waterdrinkreminder.db.historicaldata

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.waterdrinkreminder.db.HistoricalDataEntity

@Dao
interface HistoricalDataDao {

    @Query("SELECT * from historical_data")
    fun getAll(): LiveData<List<HistoricalDataEntity>>

    @Insert
    suspend fun insert(data: HistoricalDataEntity)

    @Query("DELETE FROM historical_data")
    suspend fun deleteAll()
}