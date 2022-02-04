package com.techjd.devconnector

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView
    lateinit var topAppBar: MaterialToolbar
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    private val TAG = "MAIN ACTIVITY"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Main Activity", "onCreate: Created")

        bottomNav = findViewById(R.id.bottom_navigation)
        topAppBar = findViewById(R.id.topAppBar)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController



        bottomNav.setupWithNavController(navController)
//        setCurrentFragment(HomeFragment())

        topAppBar.setNavigationOnClickListener {
            navigateToProfileActivity()
        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> {
                    navigateToSearchActuvity()
                    true
                }
                R.id.post -> {
                    val navigate = Intent(this, AddPostActivity::class.java)
                    startActivity(navigate)
                    overridePendingTransition(R.anim.slide_up, R.anim.no_animation)
                    true
                }
                R.id.messages -> {
                    navigateToMessagingActivity()
                    true
                }
                else -> false
            }
        }

//        bottomNav.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener {
//            when (it.itemId) {
//                R.id.home -> {
//                    setCurrentFragment(HomeFragment())
//                    true
//                }
//                R.id.connections -> {
//                    setCurrentFragment(ConnectionsFragment())
//                    true
//                }
//                R.id.notifications -> {
//                    setCurrentFragment(NotificationsFragment())
//                    true
//                }
//                R.id.jobs -> {
//                    setCurrentFragment(JobsFragment())
//                    true
//                }
//                else -> false
//            }
//        })
    }

//    fun setCurrentFragment(fragment: Fragment) {
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.mainFrameContainer, fragment)
//            commit()
//        }
//    }

    fun navigateToMessagingActivity() {
        val navigate = Intent(this, MessagingActivity::class.java)
        startActivity(navigate)
        overridePendingTransition(R.anim.slide_in_right, R.anim.no_animation)
    }

    fun navigateToProfileActivity() {
        val navigate = Intent(this, ProfileActivity::class.java)
        startActivity(navigate)
        overridePendingTransition(R.anim.slide_in_left, R.anim.no_animation)
    }

    fun navigateToSearchActuvity() {
        val navigate = Intent(this, SearchActivity::class.java)
        startActivity(navigate)
        overridePendingTransition(R.anim.slide_in_right, R.anim.no_animation)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Started")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: Restarted")
    }


    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Pauses")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Resumed")
    }
}