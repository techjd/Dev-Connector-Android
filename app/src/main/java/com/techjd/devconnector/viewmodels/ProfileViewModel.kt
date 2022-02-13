package com.techjd.devconnector.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techjd.devconnector.Utils.Resource
import com.techjd.devconnector.data.models.UserInfo.UserInfo
import com.techjd.devconnector.data.models.profiles.Profile
import com.techjd.devconnector.data.models.profiles.ProfileBody
import com.techjd.devconnector.data.models.profiles.ProfileItem
import com.techjd.devconnector.data.models.profiles.profileResponse.CreatedProfileResponse
import com.techjd.devconnector.respository.PostsRepository
import com.techjd.devconnector.respository.ProfileRepository
import com.techjd.devconnector.respository.UserInfoRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class ProfileViewModel : ViewModel() {
    private val _userInfo: MutableLiveData<Resource<UserInfo>> = MutableLiveData()
    val userInfo: LiveData<Resource<UserInfo>> = _userInfo
    private val _allProfile: MutableLiveData<Resource<Profile>> = MutableLiveData()
    val allProfile: LiveData<Resource<Profile>> = _allProfile
    private val _profileResponse: MutableLiveData<Resource<CreatedProfileResponse>> = MutableLiveData()
    val profileResponse: LiveData<Resource<CreatedProfileResponse>> = _profileResponse

    suspend fun getUserInfo(token: String) {
        viewModelScope.launch {
            _userInfo.value = Resource.Loading("Data is Loading")
            val response = UserInfoRepository.getUserInfo(token)
            Log.d("CODE ", "getUserInfo: ${response.code()}")
            if (response.isSuccessful) {
                _userInfo.value = Resource.Success(response.body()!!)
            } else {
                _userInfo.value = Resource.Error("Some Error Occurred")
            }
        }
    }

    suspend fun getAllProfile() {
        viewModelScope.launch {
            _allProfile.value = Resource.Loading("Data is Loading")
            val response = ProfileRepository.getAllProfiles()
            if (response.isSuccessful) {
                _allProfile.value = Resource.Success(response.body()!!)
            } else {
                _allProfile.value = Resource.Error("Some Error Occurred")
            }
        }
    }

    suspend fun createProfile(token: String, profileBody: ProfileBody) {
        viewModelScope.launch {
            _profileResponse.value = Resource.Loading("Profile is Being Created")
            val response = ProfileRepository.createProfile(token, profileBody)
            if (response.isSuccessful) {
                _profileResponse.value = Resource.Success(response.body()!!)
            } else {
                _profileResponse.value = Resource.Error("Some Error Occurred")
            }
        }
    }
}