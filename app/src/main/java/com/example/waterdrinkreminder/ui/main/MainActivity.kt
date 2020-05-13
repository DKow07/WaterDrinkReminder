package com.example.waterdrinkreminder.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.waterdrinkreminder.ui.home.HomeFragment
import com.example.waterdrinkreminder.R
import com.example.waterdrinkreminder.db.historicaldata.HistoricalDataViewModel

class MainActivity : AppCompatActivity() {

    private var homeFragment = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        switchFragment(homeFragment)
    }

    private fun switchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}
