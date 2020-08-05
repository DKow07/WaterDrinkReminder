package com.example.waterdrinkreminder.ui.daily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.waterdrinkreminder.R
import com.example.waterdrinkreminder.db.oneEntryData.EntryDataViewModel

class DailyFragment(val date: String) : Fragment(), DailyContract.View {

    private lateinit var presenter: DailyPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.daily_fragment, container, false)

//        entryDataViewModel = ViewModelProvider(this).get(EntryDataViewModel::class.java)
        setupUI(view)

        presenter = DailyPresenter(this)


        return view
    }

    private fun setupUI(view: View) {

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onSuccessDailyDataLoad() {
        TODO("Not yet implemented")
    }

    override fun onErrorDailyDataLoad() {
        TODO("Not yet implemented")
    }
}