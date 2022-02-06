package com.techjd.devconnector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.techjd.devconnector.Utils.DataStore
import com.techjd.devconnector.Utils.Status
import com.techjd.devconnector.data.models.UserPosts.NewPostResponse.NewPost
import com.techjd.devconnector.viewmodels.ProfileViewModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    lateinit var topAppBar: MaterialToolbar
    lateinit var dataStore: DataStore
    lateinit var progressBar: ProgressBar
    lateinit var userName: TextView
    lateinit var userLocation: TextView
    lateinit var userOriginalBio: TextView
    lateinit var userImg: CircleImageView
    val profileViewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        topAppBar = findViewById(R.id.topProfileBar)
        progressBar = findViewById(R.id.userProfileProgressBar)

        userName = findViewById(R.id.userName)
        userImg = findViewById(R.id.userImg)
        userLocation = findViewById(R.id.userLocation)
        userOriginalBio = findViewById(R.id.userOriginalBio)

        progressBar.visibility = View.VISIBLE
        dataStore = DataStore(this)

        lifecycleScope.launch {
            profileViewModel.getUserInfo(dataStore.getToken().first().toString())
        }

        profileViewModel.userInfo.observe(this) { userInfo ->
            when (userInfo.status) {
                Status.SUCCESS -> {
                    userName.visibility = View.VISIBLE
                    userImg.visibility = View.VISIBLE
                    userLocation.visibility = View.VISIBLE
                    userOriginalBio.visibility = View.VISIBLE

                    userName.text = userInfo.data!!.user.name
                    userLocation.text = userInfo.data.location
                    userOriginalBio.text = userInfo.data.bio

                    progressBar.visibility = View.INVISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    progressBar.visibility = View.INVISIBLE
                }
            }
        }
        topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.editProfile -> {
                    Toast.makeText(this, "Navigate To Edit Profile ", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.no_animation, R.anim.slide_out_left)
    }
}