package com.example.waterdrinkreminder.ui.home

import android.os.Bundle
import android.transition.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.waterdrinkreminder.R
import com.example.waterdrinkreminder.db.historicaldata.HistoricalDataEntity
import com.example.waterdrinkreminder.db.historicaldata.HistoricalDataViewModel

class HomeFragment : Fragment(), HomeContract.View {

    private lateinit var presenter: HomePresenter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistoricalDataAdapter
    private lateinit var currentDateText: TextView
    private lateinit var currentRemainingText: TextView
    private lateinit var currentTargetText: TextView
    private lateinit var currentPercentageVolumeText: TextView
    private lateinit var noPrevDataText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_main, container, false)

        setupUI(view)

        var viewModel = ViewModelProvider(this).get(HistoricalDataViewModel::class.java)

        viewModel.allHistoricalData.observe(viewLifecycleOwner, Observer { data ->
            data?.let{adapter.updateData(ArrayList(data))}
            checkIfEmptyDataList(data)
        })

        presenter.checkCurrentDate() //TODO: cyclic, what when app is running and 24??

        presenter.fillBasicData()

        return view
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun setCurrentDate(date: String) {
        currentDateText.text = date
    }

    override fun onFillBasicData(remaining: String, target: String, percentageVolume: String) {
        currentRemainingText.text = remaining
        currentTargetText.text = target
        currentPercentageVolumeText.text = percentageVolume
    }

    private fun setupUI(view: View) {
        presenter = HomePresenter(this, context!!)
        adapter = HistoricalDataAdapter()
        recyclerView = view.findViewById(R.id.historyRecyclerView)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter

        currentDateText = view.findViewById(R.id.currentDateText)
        currentRemainingText = view.findViewById(R.id.remainingVolumeText)
        currentTargetText = view.findViewById(R.id.targetVolumeText)
        currentPercentageVolumeText = view.findViewById(R.id.percentageVolumeText)
        noPrevDataText = view.findViewById(R.id.noHistoricalDataText)
    }

    private fun checkIfEmptyDataList(data: List<HistoricalDataEntity>) {
        if(data == null || data.isEmpty()) {
            noPrevDataText.visibility = View.VISIBLE
            recyclerView.visibility = View.INVISIBLE
        } else {
            noPrevDataText.visibility = View.INVISIBLE
            recyclerView.visibility = View.VISIBLE
        }
    }
}