package com.example.waterdrinkreminder.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.waterdrinkreminder.R
import com.example.waterdrinkreminder.db.HistoricalDataEntity
import com.example.waterdrinkreminder.db.historicaldata.AppDatabase
import com.example.waterdrinkreminder.model.HistoricalData

class HomeFragment : Fragment(), HomeContract.View {

    private lateinit var presenter: HomePresenter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_main, container, false)
        presenter = HomePresenter(this, context!!)
        recyclerView = view.findViewById(R.id.historyRecyclerView)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        presenter.loadHistoricalDataFromDb()

        return view
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    private fun populateDbTEST() {

    }

    override fun onLoadHistoricalDataSuccess(items: ArrayList<HistoricalDataEntity>) {
        var adapter: HistoryAdapter = HistoryAdapter(items, context!!)
        recyclerView.adapter = adapter
    }

    override fun onLoadHistoricalDataError() {
        TODO("Not yet implemented")
    }
}