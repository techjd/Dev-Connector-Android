package com.techjd.devconnector.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techjd.devconnector.Utils.Resource
import com.techjd.devconnector.data.models.chat.conversations.Conversations
import com.techjd.devconnector.data.models.chat.messages.Messages
import com.techjd.devconnector.data.models.chat.messages.UserId
import com.techjd.devconnector.data.models.chat.online.MakeOnlineBody
import com.techjd.devconnector.data.models.chat.online.Msg
import com.techjd.devconnector.data.models.chat.onlineusers.OnlineUsers
import com.techjd.devconnector.data.models.chat.sendMessages.SendMessageBody
import com.techjd.devconnector.data.models.chat.sendMessages.SendMessageResponse
import com.techjd.devconnector.respository.ChatRepository
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val _makeOnline: MutableLiveData<Resource<Msg>> = MutableLiveData()
    val makeOnline: LiveData<Resource<Msg>> = _makeOnline
    private val _removeOnline: MutableLiveData<Resource<Msg>> = MutableLiveData()
    val removeOnline: LiveData<Resource<Msg>> = _removeOnline
    private val _conversations: MutableLiveData<Resource<Conversations>> = MutableLiveData()
    val conversations: LiveData<Resource<Conversations>> = _conversations
    private val _onlineUsers: MutableLiveData<Resource<OnlineUsers>> = MutableLiveData()
    val onlineUsers: LiveData<Resource<OnlineUsers>> = _onlineUsers
    private val _messages: MutableLiveData<Resource<Messages>> = MutableLiveData()
    val messages: LiveData<Resource<Messages>> = _messages
    private val _sendMessage: MutableLiveData<Resource<SendMessageResponse>> = MutableLiveData()
    val sendMessage: LiveData<Resource<SendMessageResponse>> = _sendMessage

    suspend fun makeMeOnline(token: String, makeOnlineBody: MakeOnlineBody) {
        _makeOnline.value = Resource.Loading("Data is Loading")
        viewModelScope.launch {
            val response = ChatRepository.makeMeOnline(token, makeOnlineBody)
            if (response.isSuccessful) {
                _makeOnline.value = Resource.Success(response.body()!!)
            } else {
                _makeOnline.value = Resource.Error("Some Error Occurred")
            }
        }
    }

    suspend fun removeMeOnline(token: String) {
//        _removeOnline.value = Resource.Loading("Data is Loading")
        viewModelScope.launch {
            ChatRepository.removeMeOnline(token)
//            if (response.isSuccessful) {
//                _removeOnline.value = Resource.Success(response.body()!!)
//            } else {
//                _removeOnline.value = Resource.Error("Some Error Occurred")
//            }
        }
    }

    suspend fun getAllConversations(token: String) {
        _conversations.value = Resource.Loading("Data is Loading")
        viewModelScope.launch {
            val response = ChatRepository.getAllConversations(token)
            if (response.isSuccessful) {
                _conversations.value = Resource.Success(response.body()!!)
            } else {
                _conversations.value = Resource.Error("Some Error Occurred")
            }
        }
    }

    suspend fun getAllOnlineUsers(token: String) {
        _onlineUsers.value = Resource.Loading("Data is Loading")
        viewModelScope.launch {
            val response = ChatRepository.getOnlineUsers(token)
            if (response.isSuccessful) {
                _onlineUsers.value = Resource.Success(response.body()!!)
            } else {
                _onlineUsers.value = Resource.Error("Some Error Occurred")
            }
        }
    }

    suspend fun getAllMessages(token: String, toUserId: UserId) {
        _messages.value = Resource.Loading("Data is Loading")
        Log.d("TAG", "getAllMessages: REQUEST")
        viewModelScope.launch {
            val response = ChatRepository.getAllMessages(token, toUserId)
            if (response.isSuccessful) {
                _messages.value = Resource.Success(response.body()!!)
                Log.d("TAG", "getAllMessages: ${response.body()!!} ${token} ${toUserId}")

            } else {
                Log.d("TAG", "getAllMessages: ${response.message()}")
                _messages.value = Resource.Error("Some Error Occurred")
            }
        }
    }

    suspend fun sendMessage(token: String, sendMessageBody: SendMessageBody) {
        _sendMessage.value = Resource.Loading("Messaging is begin sent")
        viewModelScope.launch {
            val response = ChatRepository.sendMessage(token, sendMessageBody)
            if (response.isSuccessful) {
                _sendMessage.value = Resource.Success(response.body()!!)
            } else {
                _sendMessage.value = Resource.Error("Some Error Occurred")
            }
        }
    }
}