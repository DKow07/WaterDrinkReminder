package com.example.waterdrinkreminder.ui.main

import PreferencesHelper
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.waterdrinkreminder.R
import com.example.waterdrinkreminder.ui.home.HomeFragment
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var homeFragment = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val currentDate = PreferencesHelper.getInstance(this).getCurrentDate

        //TODO: filling first data, maybe is a better solution??
        if(currentDate == null || currentDate == "") {
            val dateFormat = SimpleDateFormat("dd.MM.yyyy")
            val date = dateFormat.format(Date())
            PreferencesHelper.getInstance(this).saveCurrentDate(date)
            PreferencesHelper.getInstance(this).saveCurrentTargetVolume(2500)
            PreferencesHelper.getInstance(this).saveCurrentRemainingVolume(0)
        }

        switchFragment(homeFragment)
    }

    fun switchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}
