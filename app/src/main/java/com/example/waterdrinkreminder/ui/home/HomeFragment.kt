package com.example.waterdrinkreminder.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.waterdrinkreminder.R
import com.example.waterdrinkreminder.db.historicaldata.HistoricalDataEntity
import com.example.waterdrinkreminder.db.historicaldata.HistoricalDataViewModel

class HomeFragment : Fragment(), HomeContract.View, View.OnClickListener, Animation.AnimationListener {

    private lateinit var presenter: HomePresenter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistoricalDataAdapter
    private lateinit var currentDateText: TextView
    private lateinit var currentRemainingText: TextView
    private lateinit var currentTargetText: TextView
    private lateinit var currentPercentageVolumeText: TextView
    private lateinit var noPrevDataText: TextView
    private lateinit var viewModel: HistoricalDataViewModel
    private lateinit var openAddWaterPanelButton: ImageView
    private lateinit var addWaterPanel: ConstraintLayout
    private lateinit var grayBackground: View
    private lateinit var plusButton: ImageView
    private lateinit var minusButton: ImageView
    private lateinit var addWaterButton: ImageView
    private lateinit var waterCupCounterText: TextView

    private var isOpenAddWaterPanel = false

    private var waterCupCounter = 0

    private val VOLUME = 250

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_main, container, false)

        viewModel = ViewModelProvider(this).get(HistoricalDataViewModel::class.java)
        setupUI(view)

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
        presenter = HomePresenter(this, context!!, viewModel)
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

        openAddWaterPanelButton = view.findViewById(R.id.openAddWaterPanelButton)
        openAddWaterPanelButton.setOnClickListener(this)
        addWaterPanel = view.findViewById(R.id.addWaterPanel)
        grayBackground = view.findViewById(R.id.grayBackground)

        plusButton = view.findViewById(R.id.plusButton)
        plusButton.setOnClickListener(this)
        minusButton = view.findViewById(R.id.minusButton)
        minusButton.setOnClickListener(this)
        addWaterButton = view.findViewById(R.id.addWaterButton)
        addWaterButton.setOnClickListener(this)
        waterCupCounterText = view.findViewById(R.id.waterCupCounterText)
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

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.openAddWaterPanelButton -> onClickOpenAddWaterPanelButton()
            R.id.minusButton -> onClickMinusButton()
            R.id.plusButton -> onClickPlusButton()
            R.id.addWaterButton -> onClickAddWaterButton()
        }
    }

    private fun onClickOpenAddWaterPanelButton() {
        if(isOpenAddWaterPanel) {
            closeAddWaterPanel()
        } else {
           showAddWaterPanel()
        }
    }

    private fun closeAddWaterPanel() {
        isOpenAddWaterPanel = false
        openAddWaterPanelButton.setImageResource(R.drawable.button_add_water_dialog_theme)
        var anim = AnimationUtils.loadAnimation(context, R.anim.fade_out)
        anim.setAnimationListener(this)
        addWaterPanel.startAnimation(anim)
        grayBackground.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
        addWaterPanel.visibility = View.INVISIBLE
        grayBackground.visibility = View.INVISIBLE

    }

    private fun showAddWaterPanel() {
        isOpenAddWaterPanel = true
        openAddWaterPanelButton.setImageResource(R.drawable.close_button_add_water_dialog_theme)
        addWaterPanel.visibility = View.VISIBLE
        grayBackground.visibility = View.VISIBLE
        addWaterPanel.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
        grayBackground.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
    }

    private fun onClickMinusButton() {
        if(waterCupCounter == 0)
            return

        waterCupCounter--
        waterCupCounterText.text = waterCupCounter.toString()
    }

    private fun onClickPlusButton() {
        if(waterCupCounter == 100)
            return

        waterCupCounter++
        waterCupCounterText.text = waterCupCounter.toString()
    }

    private fun onClickAddWaterButton() {
        val volume = VOLUME * waterCupCounter
        var remaining = PreferencesHelper.getInstance(context!!).getCurrentRemainingVolume
        var target = PreferencesHelper.getInstance(context!!).getCurrentTargetVolume
        remaining = remaining!! + volume

        PreferencesHelper.getInstance(context!!).saveCurrentRemainingVolume(remaining)

        presenter.fillBasicData()
        closeAddWaterPanel()
    }

    override fun onAnimationRepeat(animation: Animation?) {
        return
    }

    override fun onAnimationEnd(animation: Animation?) {
        waterCupCounter = 0
        waterCupCounterText.text = waterCupCounter.toString()
    }

    override fun onAnimationStart(animation: Animation?) {
        return
    }
}