package com.example.waterdrinkreminder.ui.home

import com.example.waterdrinkreminder.base.BaseContract

class HomePresenter(private var view: HomeContract.View?) : HomeContract.Presenter {

    override fun onDestroy() {
        view = null
    }
}