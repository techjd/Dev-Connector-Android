package com.techjd.devconnector.ui.fragments.Chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.gson.Gson
import com.techjd.devconnector.R
import com.techjd.devconnector.Utils.DataStore
import com.techjd.devconnector.Utils.Status
import com.techjd.devconnector.api.DevConnectorChat
import com.techjd.devconnector.data.models.chat.messages.MessagesItem
import com.techjd.devconnector.data.models.chat.messages.UserId
import com.techjd.devconnector.data.models.chat.newmessage.NewMessage
import com.techjd.devconnector.data.models.chat.sendMessages.SendMessageBody
import com.techjd.devconnector.viewmodels.ChatViewModel
import io.socket.client.Socket
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first

class MessageFragment : Fragment() {
    lateinit var topAppBar: MaterialToolbar
    lateinit var mSocket: Socket
    lateinit var mydataStore: DataStore
    lateinit var messagesRecyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var messageEditText: EditText
    lateinit var sendMsg: Button
    lateinit var chatMessageAdapter: MessageAdapter
    private val chatViewModel: ChatViewModel by viewModels()
    val args: MessageFragmentArgs by navArgs()
    lateinit var myUserId: String
    val TAG = "MessageFragment"
    val gson: Gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mydataStore = DataStore(requireContext())
        topAppBar = view.findViewById(R.id.topOnlineMessageBar)
        progressBar = view.findViewById(R.id.messageListProgressBar)
        messageEditText = view.findViewById(R.id.editTextChatMessage)
        sendMsg = view.findViewById(R.id.send)
        messagesRecyclerView = view.findViewById(R.id.messageRecyclerView)
        messagesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        topAppBar.title = ""
        mSocket = DevConnectorChat.socket


        topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        runBlocking {
            myUserId = mydataStore.getUserId().first()!!
        }

        lifecycleScope.launch {
            Log.d(TAG, "token ${mydataStore.getToken().first()!!} ${args.to}")
            chatViewModel.getAllMessages(
                mydataStore.getToken().first()!!,
                UserId(args.to)
            )
        }


        Log.d(TAG, "myUserID $myUserId")

        sendMsg.setOnClickListener {
            lifecycleScope.launch {
                chatViewModel.sendMessage(
                    mydataStore.getToken().first()!!,
                    SendMessageBody(args.to, messageEditText.text.toString())
                )
            }
        }

        chatViewModel.sendMessage.observe(viewLifecycleOwner) { message ->
            when (message.status) {
                Status.SUCCESS -> {
                    val response = message.data!!.response
                    chatMessageAdapter.addItem(
                        MessagesItem(
                            response.__v,
                            response._id,
                            response.body,
                            response.conversation,
                            response.createdAt,
                            response.from,
                            listOf(),
                            response.to,
                            listOf(),
                            response.updatedAt
                        )
                    )
                    lifecycleScope.launch {
                        withContext(Dispatchers.Main) {
                            chatMessageAdapter.notifyItemInserted(chatMessageAdapter.messages.size - 1)
                            messagesRecyclerView.scrollToPosition(chatMessageAdapter.itemCount - 1)
                        }
                    }
                    messageEditText.text.clear()
                }
                Status.ERROR -> {

                }
                Status.LOADING -> {

                }
            }
        }

        lifecycleScope.launch {
            mSocket.off("new-message").on("new-message") { message ->
                Log.d(TAG, "NEW MESSAGE ${(message[0].toString())}")
                val message = gson.fromJson(message[0].toString(), NewMessage::class.java)
                Log.d(TAG, "New Message ${message}")
                chatMessageAdapter.addItem(
                    MessagesItem(
                        0,
                        message._id,
                        message.body,
                        message.conversation,
                        message.createdAt,
                        message.from,
                        emptyList(),
                        message.to,
                        emptyList(),
                        message.updatedAt
                    )
                )
                Log.d(
                    TAG,
                    "${chatMessageAdapter.messages.size - 1} pos ${chatMessageAdapter.itemCount - 1} met ${chatMessageAdapter.returnLastPosition()}"
                )
                lifecycleScope.launch {
                    withContext(Dispatchers.Main) {
                        chatMessageAdapter.notifyItemInserted(chatMessageAdapter.messages.size - 1)
                        Log.d(
                            TAG,
                            "Whats last item HERE ${chatMessageAdapter.messages[chatMessageAdapter.messages.size - 1]}"
                        )
                        messagesRecyclerView.scrollToPosition(chatMessageAdapter.itemCount - 1)
                    }
                }
            }

            chatViewModel.messages.observe(viewLifecycleOwner) { messages ->
                when (messages.status) {
                    Status.SUCCESS -> {
                        Log.d(TAG, "onViewCreated: ${messages.data.toString()}")
                        Log.d(TAG, " Whats ${messages.data?.get(0)!!.fromObj[0].name} ")
                        if (messages.data[0].fromObj[0]._id != myUserId) {
                            topAppBar.title = messages.data[0].fromObj[0].name
                        } else {
                            topAppBar.title = messages.data[0].toObj[0].name
                        }
                        chatMessageAdapter = MessageAdapter(
                            messages.data,
                            myUserId
                        )
                        messagesRecyclerView.adapter = chatMessageAdapter
                        messagesRecyclerView.scrollToPosition(messages.data.size - 1)
                        progressBar.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}