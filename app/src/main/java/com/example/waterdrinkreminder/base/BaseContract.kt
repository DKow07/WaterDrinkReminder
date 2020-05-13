package com.example.waterdrinkreminder.base

interface BaseContract {

    interface BasePresenter {
        fun attachView()
    }

    interface BaseView {
        fun onDestroy()
    }

}