package com.techjd.devconnector.respository

import com.techjd.devconnector.api.DevConnectorService
import com.techjd.devconnector.data.models.LoginSignUp.loginResponse
import com.techjd.devconnector.data.models.LoginSignUp.loginUserDetails
import com.techjd.devconnector.data.models.LoginSignUp.registerResponse
import com.techjd.devconnector.data.models.LoginSignUp.registerUserDetails
import retrofit2.Response

object AuthRepository {
    suspend fun loginUser(email: String, password: String): Response<loginResponse> =
        DevConnectorService.devConnectorInstance.login(loginUserDetails(email, password))

    suspend fun registerUser(
        name: String,
        email: String,
        password: String
    ): Response<registerResponse> =
        DevConnectorService.devConnectorInstance.signUp(registerUserDetails(name, email, password))
}