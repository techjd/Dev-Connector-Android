package com.techjd.devconnector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.appbar.MaterialToolbar

class AddPostActivity : AppCompatActivity() {
    lateinit var toolBar: MaterialToolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        toolBar = findViewById(R.id.topAddPostBar)

        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }

        toolBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.post -> {
                    Toast.makeText(this, "Post ", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down)
    }
}