package com.techjd.devconnector.ui.activities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.techjd.devconnector.R
import com.techjd.devconnector.Utils.DataStore
import com.techjd.devconnector.Utils.Status
import com.techjd.devconnector.data.models.UserPosts.NewPostResponse.NewPost
import com.techjd.devconnector.viewmodels.PostsViewModel
import jp.wasabeef.richeditor.RichEditor
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class AddPostActivity : AppCompatActivity() {
    lateinit var toolBar: MaterialToolbar
    lateinit var mEditor: RichEditor
    lateinit var progressBar: ProgressBar
    lateinit var dataStore: DataStore
    val postsViewModel: PostsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        toolBar = findViewById(R.id.topAddPostBar)
        mEditor = findViewById(R.id.editor)
        progressBar = findViewById(R.id.postProgressBar)
        mEditor.setEditorFontSize(22);
        mEditor.setEditorFontColor(Color.BLACK);
        mEditor.setPlaceholder("Double Tap To Start Writing...");
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }

        postsViewModel.postsResponseLiveData.observe(this) { posts ->
            when (posts.status) {
                Status.SUCCESS -> {
                    Toast.makeText(this, "Post Uploaded SuccessFully", Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.INVISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    Toast.makeText(this, posts.message, Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.INVISIBLE
                }
            }

        }

        findViewById<View>(R.id.action_undo).setOnClickListener { mEditor.undo() }

        findViewById<View>(R.id.action_redo).setOnClickListener { mEditor.redo() }

        findViewById<View>(R.id.action_bold).setOnClickListener { mEditor.setBold() }

        findViewById<View>(R.id.action_italic).setOnClickListener { mEditor.setItalic() }


        findViewById<View>(R.id.action_strikethrough).setOnClickListener { mEditor.setStrikeThrough() }

        findViewById<View>(R.id.action_underline).setOnClickListener { mEditor.setUnderline() }


        findViewById<View>(R.id.action_txt_color).setOnClickListener(object : View.OnClickListener {
            private var isChanged = false
            override fun onClick(v: View) {
                mEditor.setTextColor(if (isChanged) Color.BLACK else Color.RED)
                isChanged = !isChanged
            }
        })


        findViewById<View>(R.id.action_align_left).setOnClickListener { mEditor.setAlignLeft() }

        findViewById<View>(R.id.action_align_center).setOnClickListener { mEditor.setAlignCenter() }

        findViewById<View>(R.id.action_align_right).setOnClickListener { mEditor.setAlignRight() }

        findViewById<View>(R.id.action_blockquote).setOnClickListener { mEditor.setBlockquote() }

        findViewById<View>(R.id.action_insert_bullets).setOnClickListener { mEditor.setBullets() }

        findViewById<View>(R.id.action_insert_numbers).setOnClickListener { mEditor.setNumbers() }


        dataStore = DataStore(this)

        toolBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.post -> {
                    lifecycleScope.launch {
                        postsViewModel.updatePosts(
                            dataStore.getToken().first().toString(),
                            NewPost(mEditor.html.toString())
                        )
                    }
                    progressBar.visibility = View.VISIBLE
//                    Toast.makeText(this, "Post ", Toast.LENGTH_SHORT).show()
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