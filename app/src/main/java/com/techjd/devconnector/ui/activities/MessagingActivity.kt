package com.techjd.devconnector.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.techjd.devconnector.R

class MessagingActivity : AppCompatActivity() {
    //    lateinit var topAppBar: MaterialToolbar
//    lateinit var mSocket: Socket
//    lateinit var mydataStore: DataStore
//    lateinit var conversationsRecyclerView: RecyclerView
//    lateinit var progressBar: ProgressBar
//    private val chatViewModel: ChatViewModel by viewModels()
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.chat_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
//        mydataStore = DataStore(this)
//        topAppBar = findViewById(R.id.topMessageBar)
//        progressBar = findViewById(R.id.conversationListProgressBar)
//        conversationsRecyclerView = findViewById(R.id.conversationRecyclerView)
//        conversationsRecyclerView.layoutManager = LinearLayoutManager(this)
//
//        mSocket = DevConnectorChat.socket
//
//        lifecycleScope.launch {
//            chatViewModel.getAllConversations(mydataStore.getToken().first()!!)
//        }
//        Log.d("SOCKET ID", "${mSocket.id()} Hello")

//        chatViewModel.conversations.observe(this) { conversations ->
//            when (conversations.status) {
//                Status.SUCCESS -> {
//                    conversationsRecyclerView.adapter = ConversationsAdapter(conversations.data!!)
//                    progressBar.visibility = View.GONE
//                }
//                Status.LOADING -> {
//                    progressBar.visibility = View.VISIBLE
//                }
//                Status.ERROR -> {
//                    progressBar.visibility = View.GONE
//                }
//            }
//        }

//        topAppBar.setNavigationOnClickListener {
//            onBackPressed()
//        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.no_animation, R.anim.slide_out_right)
        Log.d("", "onBackPressed: ")
    }
}