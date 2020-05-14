package com.example.waterdrinkreminder.db.historicaldata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
                    val historicalDataDao = database?.HistoricalDataDao()

                    //historicalDataDao?.deleteAll()

//                    historicalDataDao?.insert(HistoricalDataEntity( "13.05.2020", 2750, 2500))
//                    historicalDataDao?.insert(HistoricalDataEntity("12.05.2020", 2500, 2500))
//                    historicalDataDao?.insert(HistoricalDataEntity(null,"8.05.2020", 750, 2500))
//                    historicalDataDao?.insert(HistoricalDataEntity(null,"7.05.2020", 750, 2500))
//                    historicalDataDao?.insert(HistoricalDataEntity(null,"6.05.2020", 750, 2500))
//                    historicalDataDao?.insert(HistoricalDataEntity(null,"5.05.2020", 1000, 2500))
//                    historicalDataDao?.insert(HistoricalDataEntity(null,"4.05.2020", 1750, 2500))
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
                ).addCallback(AppDataBaseCallback(scope)).build()
                this.instance = instance
                return instance
            }
        }
    }
}