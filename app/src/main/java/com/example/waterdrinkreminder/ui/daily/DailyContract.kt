package com.example.waterdrinkreminder.ui.daily

interface DailyContract {
    interface View {
        fun onSuccessDailyDataLoad()

        fun onErrorDailyDataLoad()
    }

    interface Presenter {
        fun loadDailyData(currDate: String)
    }
}