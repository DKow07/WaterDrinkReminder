package com.example.waterdrinkreminder.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.waterdrinkreminder.R
import com.example.waterdrinkreminder.model.HistoryData
import kotlinx.android.synthetic.main.fragment_main.*

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
        presenter = HomePresenter(this)
        recyclerView = view.findViewById(R.id.historyRecyclerView)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        //----------------------------test------------------------------------
        var testItems = ArrayList<HistoryData>()
        testItems.add(HistoryData("11.05.2020", 10, "250ml/2500ml"))
        testItems.add(HistoryData("10.05.2020", 20, "1250ml/2500ml"))
        testItems.add(HistoryData("9.05.2020", 40, "250ml/2500ml"))
        testItems.add(HistoryData("8.05.2020", 75, "750ml/2500ml"))
        testItems.add(HistoryData("7.05.2020", 75, "750ml/2500ml"))
        testItems.add(HistoryData("6.05.2020", 75, "750ml/2500ml"))
        testItems.add(HistoryData("5.05.2020", 75, "750ml/2500ml"))
        testItems.add(HistoryData("4.05.2020", 75, "750ml/2500ml"))
        var testAdapter = HistoryAdapter(testItems, context!!)
        //----------------------------test------------------------------------

        recyclerView.adapter = testAdapter

        return view
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}