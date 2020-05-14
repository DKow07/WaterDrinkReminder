package com.example.waterdrinkreminder.ui.home

import com.example.waterdrinkreminder.base.BaseContract

interface HomeContract {

    interface View : BaseContract.BaseView {
        fun setCurrentDate(date: String)

        fun onFillBasicData(remaining: String, target: String, percentageVolume: String)
    }

    interface Presenter : BaseContract.BasePresenter {
        fun fillBasicData()

        fun checkCurrentDate()
    }
}