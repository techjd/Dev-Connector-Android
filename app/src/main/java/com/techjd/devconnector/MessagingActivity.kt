package com.techjd.devconnector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.AnyThread
import com.google.android.material.appbar.MaterialToolbar

class MessagingActivity : AppCompatActivity() {
    lateinit var topAppBar: MaterialToolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)

        topAppBar = findViewById(R.id.topMessageBar)

        topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.no_animation, R.anim.slide_out_right)
        Log.d("", "onBackPressed: ")
    }
}