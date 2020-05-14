package com.example.waterdrinkreminder.ui.home

import android.content.Context
import com.example.waterdrinkreminder.db.historicaldata.HistoricalDataViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class HomePresenter(private var view: HomeContract.View?, private var context: Context) : HomeContract.Presenter {

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy")

    override fun fillBasicData() {
        var remaining = PreferencesHelper.getInstance(context).getCurrentRemainingVolume
        var target = PreferencesHelper.getInstance(context).getCurrentTargetVolume
        var percentageVolume = ((remaining!!.toFloat() / target!!.toFloat()) * 100).toInt()

        var remainingText = remaining.toString()+"ml"
        var targetText = target.toString()+"ml"
        var percentageVolumeText = percentageVolume.toString()+"%"

        view?.onFillBasicData(remainingText, targetText, percentageVolumeText)
        //TODO: if data are empty what??
    }

    override fun checkCurrentDate() {
        val date = dateFormat.format(Date())
        view?.setCurrentDate(date)

        if(checkIfIsNewDate(date)) {
            //saveDataToDB
            PreferencesHelper.getInstance(context).saveCurrentDate(date)
            //clear volume and percentageVolume
        }
    }

    private fun checkIfIsNewDate(newDate: String): Boolean {
        val oldDate = PreferencesHelper.getInstance(context).getCurrentDate
        var oldLocalDate: Date? = null
        var newLocalDate: Date? = null

        if(oldDate != "")
            oldLocalDate = dateFormat.parse(oldDate)
        if(newDate != "")
            newLocalDate = dateFormat.parse(newDate)

        return newLocalDate!!.after(oldLocalDate)
    }

    override fun onDestroy() {
        view = null
    }
}