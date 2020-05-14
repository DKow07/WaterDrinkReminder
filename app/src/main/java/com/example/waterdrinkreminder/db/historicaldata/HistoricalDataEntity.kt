package com.example.waterdrinkreminder.db.historicaldata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historical_data")
data class HistoricalDataEntity (
//    @PrimaryKey(autoGenerate = true)
//    var id: Int?,

    @PrimaryKey @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "remaining_volume") var remainingVolume: Int,
    @ColumnInfo(name = "target_volume") var targetVolume: Int

)