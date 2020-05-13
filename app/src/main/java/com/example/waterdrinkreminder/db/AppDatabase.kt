package com.example.waterdrinkreminder.db.historicaldata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.waterdrinkreminder.db.HistoricalDataEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [HistoricalDataEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun HistoricalDataDao(): HistoricalDataDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        private class AppDataBaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                instance.let { database -> scope.launch {
                    var historicalDataDao = database?.HistoricalDataDao()

                    historicalDataDao?.deleteAll()

                    historicalDataDao?.insert(HistoricalDataEntity(null, "11.05.2020", 10, "250ml/2500ml"))
                    historicalDataDao?.insert(HistoricalDataEntity(null,"10.05.2020", 20, "1250ml/2500ml"))
                    historicalDataDao?.insert(HistoricalDataEntity(null,"8.05.2020", 12, "750ml/2500ml"))
                    historicalDataDao?.insert(HistoricalDataEntity(null,"7.05.2020", 5, "750ml/2500ml"))
                    historicalDataDao?.insert(HistoricalDataEntity(null,"6.05.2020", 76, "750ml/2500ml"))
                    historicalDataDao?.insert(HistoricalDataEntity(null,"5.05.2020", 4, "750ml/2500ml"))
                    historicalDataDao?.insert(HistoricalDataEntity(null,"4.05.2020", 99, "750ml/2500ml"))
                }}
            }
        }

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            val tempInstance = instance
            if(tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "water-drink-reminder"
                ).build()
                this.instance = instance
                return instance
            }
        }
    }
}