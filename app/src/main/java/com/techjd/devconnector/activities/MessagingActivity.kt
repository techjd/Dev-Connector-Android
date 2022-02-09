package com.techjd.devconnector.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.techjd.devconnector.R
import com.techjd.devconnector.Utils.DataStore
import com.techjd.devconnector.Utils.Resource
import com.techjd.devconnector.Utils.Status
import com.techjd.devconnector.api.DevConnectorChat
import com.techjd.devconnector.viewmodels.ChatViewModel
import io.socket.client.Socket
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MessagingActivity : AppCompatActivity() {
    lateinit var topAppBar: MaterialToolbar
    lateinit var mSocket: Socket
    lateinit var mydataStore: DataStore
    lateinit var conversationsRecyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    private val chatViewModel: ChatViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)

        mydataStore = DataStore(this)
        topAppBar = findViewById(R.id.topMessageBar)
        progressBar = findViewById(R.id.conversationListProgressBar)
        conversationsRecyclerView = findViewById(R.id.conversationRecyclerView)
        conversationsRecyclerView.layoutManager = LinearLayoutManager(this)

        mSocket = DevConnectorChat.socket

        lifecycleScope.launch {
            chatViewModel.getAllConversations(mydataStore.getToken().first()!!)
        }
        Log.d("SOCKET ID", "${mSocket.id()} Hello")

        chatViewModel.conversations.observe(this) { conversations ->
            when (conversations.status) {
                Status.SUCCESS -> {
                    conversationsRecyclerView.adapter = ConversationsAdapter(conversations.data!!)
                    progressBar.visibility = View.GONE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                }
            }
        }

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