package com.example.waterdrinkreminder.ui.home

import com.example.waterdrinkreminder.base.BaseContract
import com.example.waterdrinkreminder.db.HistoricalDataEntity

interface HomeContract {

    interface View : BaseContract.BaseView {
        fun onLoadHistoricalDataSuccess(items: ArrayList<HistoricalDataEntity>)

        fun onLoadHistoricalDataError()
    }

    interface Presenter : BaseContract.BasePresenter {
        fun loadHistoricalDataFromDb()
    }
}