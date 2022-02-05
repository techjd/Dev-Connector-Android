package com.techjd.devconnector.api

import com.techjd.devconnector.data.models.LoginSignUp.loginResponse
import com.techjd.devconnector.data.models.LoginSignUp.loginUserDetails
import com.techjd.devconnector.data.models.LoginSignUp.registerResponse
import com.techjd.devconnector.data.models.LoginSignUp.registerUserDetails
import com.techjd.devconnector.data.models.UserPosts.NewPostResponse.NewPost
import com.techjd.devconnector.data.models.UserPosts.NewPostResponse.PostResponse
import com.techjd.devconnector.data.models.UserPosts.Posts
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
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

    @GET("posts")
    suspend fun getPosts(
        @Header("x-auth-token") token: String
    ): Response<Posts>

    @POST("posts")
    suspend fun updatePosts(
        @Header("x-auth-token")  token: String,
        @Body newPost: NewPost
    ): Response<PostResponse>


}