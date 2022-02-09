package com.techjd.devconnector.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techjd.devconnector.Utils.Resource
import com.techjd.devconnector.data.models.chat.conversations.Conversations
import com.techjd.devconnector.data.models.chat.online.MakeOnlineBody
import com.techjd.devconnector.data.models.chat.online.Msg
import com.techjd.devconnector.respository.ChatRepository
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val _makeOnline: MutableLiveData<Resource<Msg>> = MutableLiveData()
    val makeOnline: LiveData<Resource<Msg>> = _makeOnline
    private val _removeOnline: MutableLiveData<Resource<Msg>> = MutableLiveData()
    val removeOnline: LiveData<Resource<Msg>> = _removeOnline
    private val _conversations: MutableLiveData<Resource<Conversations>> = MutableLiveData()
    val conversations: LiveData<Resource<Conversations>> = _conversations

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
        _removeOnline.value = Resource.Loading("Data is Loading")
        viewModelScope.launch {
            val response = ChatRepository.removeMeOnline(token)
            if (response.isSuccessful) {
                _removeOnline.value = Resource.Success(response.body()!!)
            } else {
                _removeOnline.value = Resource.Error("Some Error Occurred")
            }
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


}