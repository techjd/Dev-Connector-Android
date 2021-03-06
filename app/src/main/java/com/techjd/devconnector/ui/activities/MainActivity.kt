package com.techjd.devconnector.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.techjd.devconnector.R
import com.techjd.devconnector.Utils.DataStore
import com.techjd.devconnector.ui.ModalBottomSheet
import com.techjd.devconnector.viewmodels.ChatViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView
    lateinit var topAppBar: MaterialToolbar
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    lateinit var mydataStore: DataStore
    private val TAG = "MAIN ACTIVITY"
    val chatViewModel: ChatViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Main Activity", "onCreate: Created")

        mydataStore = DataStore(this)
        bottomNav = findViewById(R.id.bottom_navigation)
        topAppBar = findViewById(R.id.topAppBar)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
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
                    val bottomSheet = ModalBottomSheet()
                    bottomSheet.show(supportFragmentManager, ModalBottomSheet.TAG)
                    true
//                    val navigate = Intent(this, AddPostActivity::class.java)
//                    startActivity(navigate)
//                    overridePendingTransition(R.anim.slide_up, R.anim.no_animation)
//                    true
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

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Destroyed")
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.homeFragment) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Are You Sure You Want To Exit ? ")
                .setPositiveButton(
                    "Yes"
                ) { dialog, id ->
                    super.onBackPressed()
                    GlobalScope.launch {
                        chatViewModel.removeMeOnline(mydataStore.getToken().first()!!)
                    }
                    finish()
                }
                .setNegativeButton(
                    "No"
                ) { dialog, id ->
                    dialog.dismiss()
                }
            builder.setCancelable(false)
            builder.create()
            builder.show()
        } else {
            if (navController.currentDestination?.id == R.id.fragment_detail_post) {
                navController.popBackStack()
                topAppBar.visibility = View.VISIBLE
                bottomNav.visibility = View.VISIBLE
            } else {
                super.onBackPressed()
            }
        }
    }
}