package com.techjd.devconnector.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techjd.devconnector.Utils.Resource
import com.techjd.devconnector.data.models.LoginSignUp.registerResponse
import com.techjd.devconnector.respository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class SignUpViewModel : ViewModel() {
    private val _firstName = MutableLiveData("")
    private val _secondName = MutableLiveData("")
    private val _email = MutableLiveData("")
    private val _password = MutableLiveData("")
    private val _response = MutableLiveData<Resource<registerResponse>>()
    val firstName: LiveData<String> = _firstName
    val secondName: LiveData<String> = _secondName
    val email: LiveData<String> = _email
    val password: LiveData<String> = _password
    val resource: LiveData<Resource<registerResponse>> = _response

    fun saveFirstNameAndSecond(firstName: String, secondName: String) {
        _firstName.value = firstName
        _secondName.value = secondName
    }

    fun getName(): String? {
        return firstName.value
    }

    fun saveEmail(email: String) {
        Log.d("EMAIL", "saveEmail: ${email}")
        _email.value = email
    }

    fun savePassword(password: String) {
        _password.value = password
    }

    suspend fun registerUser() {
        _response.value = Resource.Loading(message = "Registering User")
        viewModelScope.launch {
            val response = AuthRepository.registerUser(
                name = firstName.value + secondName.value,
                email = email.value!!,
                password = password.value!!
            )
            if (response.isSuccessful) {
                _response.value = Resource.Success(response.body()!!)
            } else {
                _response.value = Resource.Error("Some Error Occurred")
            }
        }
    }
}