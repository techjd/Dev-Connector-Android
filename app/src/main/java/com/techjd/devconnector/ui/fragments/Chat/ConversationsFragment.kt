package com.techjd.devconnector.ui.fragments.Chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.techjd.devconnector.R
import com.techjd.devconnector.Utils.DataStore
import com.techjd.devconnector.Utils.Status
import com.techjd.devconnector.ui.activities.MessagingActivity
import com.techjd.devconnector.api.DevConnectorChat
import com.techjd.devconnector.viewmodels.ChatViewModel
import io.socket.client.Socket
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ConversationsFragment : Fragment() {
    lateinit var topAppBar: MaterialToolbar
    lateinit var mSocket: Socket
    lateinit var mydataStore: DataStore
    lateinit var conversationsRecyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var floatingActionButton: ExtendedFloatingActionButton
    lateinit var myuserid: String
    private val chatViewModel: ChatViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_conversations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mydataStore = DataStore(requireContext())
        topAppBar = view.findViewById(R.id.topMessageBar)
        progressBar = view.findViewById(R.id.conversationListProgressBar)
        floatingActionButton = view.findViewById(R.id.floatingActionButton)
        conversationsRecyclerView = view.findViewById(R.id.conversationRecyclerView)
        conversationsRecyclerView.layoutManager = LinearLayoutManager(context)

        runBlocking {
            myuserid = mydataStore.getUserId().first()!!
        }

        mSocket = DevConnectorChat.socket

        floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_conversationsFragment_to_onlineUsersFragment)
        }

        topAppBar.setNavigationOnClickListener {
//            requireActivity().onBackPressed()
            (activity as MessagingActivity).onBackPressed()
        }

        lifecycleScope.launch {
            chatViewModel.getAllConversations(mydataStore.getToken().first()!!)
        }
        Log.d("SOCKET ID", "${mSocket.id()} Hello")

        chatViewModel.conversations.observe(viewLifecycleOwner) { conversations ->
            when (conversations.status) {
                Status.SUCCESS -> {
                    conversationsRecyclerView.adapter = ConversationsAdapter(
                        conversations.data!!,
                        onClickListener = { view, id, toName ->
                            val action =
                                ConversationsFragmentDirections.actionConversationsFragmentToMessageFragment(
                                    id,
                                    toName
                                )
                            findNavController().navigate(action)
//                            makeToast(view, id)
                        },
                        myUserId = myuserid
                    )
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

    private fun makeToast(view: View, text: String) {
        Toast.makeText(view.context, text, Toast.LENGTH_SHORT).show()
    }
}