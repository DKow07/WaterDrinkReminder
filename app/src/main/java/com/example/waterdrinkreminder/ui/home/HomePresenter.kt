package com.example.waterdrinkreminder.ui.home

import PreferencesHelper
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.viewModelScope
import com.example.waterdrinkreminder.db.historicaldata.AppDatabase
import com.example.waterdrinkreminder.db.historicaldata.HistoricalDataEntity
import com.example.waterdrinkreminder.db.historicaldata.HistoricalDataViewModel
import com.example.waterdrinkreminder.db.oneEntryData.EntryDataEntity
import com.example.waterdrinkreminder.db.oneEntryData.EntryDataViewModel
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class HomePresenter(private var view: HomeContract.View?, private var context: Context, private val historicalDataViewModel: HistoricalDataViewModel, private val entryViewModel: EntryDataViewModel) : HomeContract.Presenter {

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy")

    override fun fillBasicData() {
        val remaining = PreferencesHelper.getInstance(context).getCurrentRemainingVolume
        val target = PreferencesHelper.getInstance(context).getCurrentTargetVolume
        val percentageVolume = ((remaining!!.toFloat() / target!!.toFloat()) * 100).toInt()

        val remainingText = remaining.toString()+"ml"
        val targetText = target.toString()+"ml"
        val percentageVolumeText = percentageVolume.toString()+"%"

        view?.onFillBasicData(remainingText, targetText, percentageVolumeText)
        //TODO: if data are empty what??
    }

    override fun checkCurrentDate() {
        val date = dateFormat.format(Date())
        view?.setCurrentDate(date)

        if(checkIfIsNewDate(date)) {
            saveDataToDB()
            PreferencesHelper.getInstance(context).saveCurrentDate(date)
            clearPreferencesForNewDay()
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

    private fun saveDataToDB() {
        val remaining = PreferencesHelper.getInstance(context).getCurrentRemainingVolume
        val target = PreferencesHelper.getInstance(context).getCurrentTargetVolume
        val date = PreferencesHelper.getInstance(context).getCurrentDate

        if(date != null && remaining != null && target != null) {
            val data = HistoricalDataEntity( date, remaining, target)
            try {
                historicalDataViewModel.insert(data)
            } catch(exception: Exception) {

            }
        }
    }

    private fun clearPreferencesForNewDay() {
        PreferencesHelper.getInstance(context).saveCurrentRemainingVolume(0)
    }

    override fun onDestroy() {
        view = null
    }

    fun saveEntryDataToDb(type:String, volume:Int) {
        val date = PreferencesHelper.getInstance(context).getCurrentDate
        val data = EntryDataEntity(null, date!!, type, volume)
        entryViewModel.insert(data)
    }
}