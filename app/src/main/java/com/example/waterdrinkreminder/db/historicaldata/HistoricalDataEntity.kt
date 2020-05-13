package com.example.waterdrinkreminder.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historical_data")
data class HistoricalDataEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int?,

    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "percentage_volume") var percentageVolume: Int,
    @ColumnInfo(name = "volume") var volume: String

)