package com.techjd.devconnector.fragments.HomeScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.techjd.devconnector.PostsAdapter
import com.techjd.devconnector.R
import com.techjd.devconnector.Utils.DataStore
import com.techjd.devconnector.Utils.Status
import com.techjd.devconnector.api.DevConnectorChat
import com.techjd.devconnector.data.models.chat.online.MakeOnlineBody
import com.techjd.devconnector.viewmodels.ChatViewModel
import com.techjd.devconnector.viewmodels.PostsViewModel
import io.socket.client.Socket
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeFragment : Fragment() {

    lateinit var materialToolbar: MaterialToolbar
    lateinit var postsRecyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var mydataStore: DataStore
    lateinit var mSocket: Socket
    val postsViewModel: PostsViewModel by viewModels()
    val chatViewModel: ChatViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mydataStore = DataStore(requireContext())


        mSocket = DevConnectorChat.socket
        mSocket.connect()

        runBlocking {
            var isNull = true;
            while (isNull) {
                if (mSocket.id() != null) {
                    isNull = false
                }
            }
        }

        lifecycleScope.launch {
            chatViewModel.makeMeOnline(
                mydataStore.getToken().first()!!,
                MakeOnlineBody(mSocket.id())
            )
        }

        Log.d("SOCKET ID", "${mSocket.id()} Hello")

        postsRecyclerView = view.findViewById(R.id.postsRecyclerView)
        progressBar = view.findViewById(R.id.progressBar2)
        postsRecyclerView.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launch {
            postsViewModel.getPosts(mydataStore.getToken().first()!!)
        }

        postsViewModel.postLiveData.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    postsRecyclerView.adapter = PostsAdapter(response.data!!)
                    progressBar.visibility = View.GONE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    Log.d("Home Fragment", "onViewCreated: ${response.status}")

                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    Log.d("Home Fragment", "onViewCreated: ${response.status}")

                }
            }
        }


    }

}