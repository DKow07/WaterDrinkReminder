package com.example.waterdrinkreminder.ui.home

import com.example.waterdrinkreminder.base.BaseContract
import com.example.waterdrinkreminder.db.HistoricalDataEntity

interface HomeContract {

    interface View : BaseContract.BaseView {
        fun setCurrentDate(date: String)
    }

    interface Presenter : BaseContract.BasePresenter {
        //TODO: fillBasicData

        fun checkCurrentDate()
    }
}