package com.techjd.devconnector.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techjd.devconnector.Utils.Resource
import com.techjd.devconnector.data.models.UserInfo.UserInfo
import com.techjd.devconnector.respository.PostsRepository
import com.techjd.devconnector.respository.UserInfoRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class ProfileViewModel : ViewModel() {
    private val _userInfo: MutableLiveData<Resource<UserInfo>> = MutableLiveData()
    var userInfo: LiveData<Resource<UserInfo>> = _userInfo

    suspend fun getUserInfo(token: String) {
        viewModelScope.launch {
            _userInfo.value = Resource.Loading("Data is Loading")
            val response = UserInfoRepository.getUserInfo(token)
            if (response.isSuccessful) {
                _userInfo.value = Resource.Success(response.body()!!)
            } else {
                _userInfo.value = Resource.Error("Some Error Occurred")
            }
        }
    }
}