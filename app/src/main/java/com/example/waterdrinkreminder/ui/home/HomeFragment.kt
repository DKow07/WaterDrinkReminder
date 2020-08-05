package com.example.waterdrinkreminder.ui.home

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.waterdrinkreminder.R
import com.example.waterdrinkreminder.db.historicaldata.HistoricalDataEntity
import com.example.waterdrinkreminder.db.historicaldata.HistoricalDataViewModel
import com.example.waterdrinkreminder.db.oneEntryData.EntryDataViewModel
import com.example.waterdrinkreminder.ui.daily.DailyFragment
import com.example.waterdrinkreminder.ui.daily.DailyPresenter
import com.example.waterdrinkreminder.ui.main.MainActivity

class HomeFragment : Fragment(), HomeContract.View, View.OnClickListener, Animation.AnimationListener, HistoricalDataListener {

    private lateinit var presenter: HomePresenter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistoricalDataAdapter
    private lateinit var currentDateText: TextView
    private lateinit var currentRemainingText: TextView
    private lateinit var currentTargetText: TextView
    private lateinit var currentPercentageVolumeText: TextView
    private lateinit var noPrevDataText: TextView
    private lateinit var historicalDataViewModel: HistoricalDataViewModel
    private lateinit var entryDataViewModel: EntryDataViewModel
    private lateinit var openAddWaterPanelButton: ImageView
    private lateinit var currentDayDropImageView: ImageView
    private lateinit var addWaterPanel: ConstraintLayout
    private lateinit var grayBackground: View
    private lateinit var addWaterButton: ImageView
    private lateinit var spinnerType: Spinner
    private lateinit var editTextMlVolume: EditText

    private var isOpenAddWaterPanel = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_main, container, false)

        historicalDataViewModel = ViewModelProvider(this).get(HistoricalDataViewModel::class.java)
        entryDataViewModel = ViewModelProvider(this).get(EntryDataViewModel::class.java)
        setupUI(view)

        historicalDataViewModel.allHistoricalData.observe(viewLifecycleOwner, Observer { data ->
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
        presenter = HomePresenter(this, context!!, historicalDataViewModel, entryDataViewModel)
        adapter = HistoricalDataAdapter(this)
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

        addWaterButton = view.findViewById(R.id.addWaterButton)
        addWaterButton.setOnClickListener(this)

        spinnerType = view.findViewById(R.id.spinnerType)
        val types = mutableListOf("Coffee", "Tea", "Water")
        val spinnerTypeAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, types)
        spinnerType.adapter = spinnerTypeAdapter

        editTextMlVolume = view.findViewById(R.id.editTextMlVolume)

        currentDayDropImageView = view.findViewById(R.id.dropImageView)
        currentDayDropImageView.setOnClickListener{
            onHistoricalDataClickListener(HistoricalDataEntity(currentDateText.text.toString(), 0, 0))
        }
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
        val anim = AnimationUtils.loadAnimation(context, R.anim.fade_out)
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

    private fun onClickAddWaterButton() {
        val volume = editTextMlVolume.text.toString().toInt()
        var remaining = PreferencesHelper.getInstance(context!!).getCurrentRemainingVolume
        var target = PreferencesHelper.getInstance(context!!).getCurrentTargetVolume
        remaining = remaining!! + volume

        PreferencesHelper.getInstance(context!!).saveCurrentRemainingVolume(remaining)

        presenter.fillBasicData()

        val type = spinnerType.selectedItem.toString()

        presenter.saveEntryDataToDb(type, volume)

        closeAddWaterPanel()
        hideKeyboard()
    }

    override fun onAnimationRepeat(animation: Animation?) {
        return
    }

    override fun onAnimationEnd(animation: Animation?) {
        return
    }

    override fun onAnimationStart(animation: Animation?) {
        return
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onHistoricalDataClickListener(historicalDataEntity: HistoricalDataEntity) {
//        val toast = Toast.makeText(context, "Date " + historicalDataEntity.date, Toast.LENGTH_SHORT)
//        toast.show()
        val dailyFragment = DailyFragment(historicalDataEntity.date)
        (activity as MainActivity).switchFragment(dailyFragment)
    }
}