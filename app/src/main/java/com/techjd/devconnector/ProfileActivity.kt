package com.techjd.devconnector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.android.material.appbar.MaterialToolbar

class ProfileActivity : AppCompatActivity() {
    lateinit var topAppBar: MaterialToolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        topAppBar = findViewById(R.id.topProfileBar)
        topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.no_animation, R.anim.slide_out_left)
    }
}