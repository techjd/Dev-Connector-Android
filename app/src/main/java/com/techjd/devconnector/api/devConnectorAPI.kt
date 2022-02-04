package com.techjd.devconnector.api

import com.techjd.devconnector.data.models.LoginSignUp.loginResponse
import com.techjd.devconnector.data.models.LoginSignUp.loginUserDetails
import com.techjd.devconnector.data.models.LoginSignUp.registerResponse
import com.techjd.devconnector.data.models.LoginSignUp.registerUserDetails
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface devConnectorAPI {

    @POST("auth")
    suspend fun login(
        @Body loginUserDetails: loginUserDetails
    ): Response<loginResponse>

    @POST("users")
    suspend fun signUp(
        @Body registerUserDetails: registerUserDetails
    ): Response<registerResponse>

    suspend fun getPosts()


}