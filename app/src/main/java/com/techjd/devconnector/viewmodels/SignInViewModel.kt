package com.techjd.devconnector.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techjd.devconnector.respository.AuthRepository
import com.techjd.devconnector.Utils.Resource
import com.techjd.devconnector.data.models.LoginSignUp.loginResponse
import kotlinx.coroutines.launch

class SignInViewModel(

) : ViewModel() {
    private val _response: MutableLiveData<Resource<loginResponse>> = MutableLiveData()
    val _responseLiveData: LiveData<Resource<loginResponse>> = _response

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _response.value = Resource.Loading(message = "Data is Loading")
            val response = AuthRepository.loginUser(email, password)
            if (response.isSuccessful) {
                _response.value = Resource.Success(data = response.body()!!)
            } else {
                _response.value = Resource.Error(message = "Some Error Occurred")
            }
        }
    }
}