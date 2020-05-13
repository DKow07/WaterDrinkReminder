package com.example.waterdrinkreminder.ui.home

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.waterdrinkreminder.base.BaseContract
import com.example.waterdrinkreminder.db.historicaldata.AppDatabase
import com.example.waterdrinkreminder.db.historicaldata.HistoricalDataViewModel

class HomePresenter(private var view: HomeContract.View?, private var context: Context, private var activity: AppCompatActivity) : HomeContract.Presenter {

    private lateinit var historicalDataViewModel: HistoricalDataViewModel

    override fun loadHistoricalDataFromDb() {
        historicalDataViewModel = ViewModelProvider(activity).get(HistoricalDataViewModel::class.java)

    }

    override fun onDestroy() {
        view = null
    }
}