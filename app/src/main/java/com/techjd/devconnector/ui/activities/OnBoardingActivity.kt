package com.techjd.devconnector.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.techjd.devconnector.R

class OnBoardingActivity : AppCompatActivity() {

    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.on_boarding_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

    }
}