package com.example.waterdrinkreminder.ui.home

import android.os.Bundle
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
import com.example.waterdrinkreminder.db.HistoricalDataEntity
import com.example.waterdrinkreminder.db.historicaldata.HistoricalDataViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class HomeFragment : Fragment(), HomeContract.View {

    private lateinit var presenter: HomePresenter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistoricalDataAdapter
    private lateinit var currentDateText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_main, container, false)

        presenter = HomePresenter(this, context!!)
        adapter = HistoricalDataAdapter()
        recyclerView = view.findViewById(R.id.historyRecyclerView)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter

        currentDateText = view.findViewById(R.id.currentDateText)

        var viewModel = ViewModelProvider(this).get(HistoricalDataViewModel::class.java)

        viewModel.allHistoricalData.observe(viewLifecycleOwner, Observer { data ->
            data?.let{adapter.updateData(ArrayList(data))}
            //checkIfEmpty
        })

        presenter.checkCurrentDate() //TODO: cyclic, what when app is running and 24??

        return view
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun setCurrentDate(date: String) {
        currentDateText.text = date
    }
}