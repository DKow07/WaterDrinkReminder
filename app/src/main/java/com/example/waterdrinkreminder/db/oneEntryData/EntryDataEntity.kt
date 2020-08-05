package com.example.waterdrinkreminder.db.oneEntryData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entry_data")
data class EntryDataEntity (
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "volume") var volume: Int
)