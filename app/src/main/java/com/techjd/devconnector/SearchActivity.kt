package com.techjd.devconnector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.android.material.appbar.MaterialToolbar

class SearchActivity : AppCompatActivity() {
    lateinit var topSearchBar: MaterialToolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        topSearchBar = findViewById(R.id.topSearchBar)
        topSearchBar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.no_animation, R.anim.slide_out_right)
    }
}