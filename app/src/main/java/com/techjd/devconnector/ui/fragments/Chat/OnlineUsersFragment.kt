package com.techjd.devconnector.ui.fragments.Chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.techjd.devconnector.R
import com.techjd.devconnector.Utils.DataStore
import com.techjd.devconnector.Utils.Status
import com.techjd.devconnector.api.DevConnectorChat
import com.techjd.devconnector.viewmodels.ChatViewModel
import io.socket.client.Socket
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class OnlineUsersFragment : Fragment() {
    lateinit var topAppBar: MaterialToolbar
    lateinit var mSocket: Socket
    lateinit var mydataStore: DataStore
    lateinit var onlineUsersRecyclerView: RecyclerView
    lateinit var noOnlineUsers: TextView
    lateinit var progressBar: ProgressBar
    private val chatViewModel: ChatViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_online_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mydataStore = DataStore(requireContext())
        topAppBar = view.findViewById(R.id.topOnlineUserBar)
        progressBar = view.findViewById(R.id.onlineUsersProgressBar)
        onlineUsersRecyclerView = view.findViewById(R.id.onlineUsersRecyclerView)
        onlineUsersRecyclerView.layoutManager = LinearLayoutManager(context)

        mSocket = DevConnectorChat.socket

        topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        lifecycleScope.launch {
            chatViewModel.getAllOnlineUsers(mydataStore.getToken().first()!!)
        }

        chatViewModel.onlineUsers.observe(viewLifecycleOwner) { onlineUsers ->
            when (onlineUsers.status) {
                Status.SUCCESS -> {
                    onlineUsersRecyclerView.adapter = OnlineUsersAdapter(onlineUsers.data!!)
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

    }
}