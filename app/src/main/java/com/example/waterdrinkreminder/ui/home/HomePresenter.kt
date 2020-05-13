package com.example.waterdrinkreminder.ui.home

import android.content.Context
import com.example.waterdrinkreminder.db.historicaldata.HistoricalDataViewModel

class HomePresenter(private var view: HomeContract.View?, private var context: Context) : HomeContract.Presenter {

    override fun loadHistoricalDataFromDb() {

    }

    override fun onDestroy() {
        view = null
    }
}