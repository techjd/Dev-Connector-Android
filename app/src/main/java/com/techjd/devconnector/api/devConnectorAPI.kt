package com.techjd.devconnector.api

import com.techjd.devconnector.data.models.LoginSignUp.loginResponse
import com.techjd.devconnector.data.models.LoginSignUp.loginUserDetails
import com.techjd.devconnector.data.models.LoginSignUp.registerResponse
import com.techjd.devconnector.data.models.LoginSignUp.registerUserDetails
import com.techjd.devconnector.data.models.UserInfo.UserInfo
import com.techjd.devconnector.data.models.UserPosts.NewPostResponse.NewPost
import com.techjd.devconnector.data.models.UserPosts.NewPostResponse.PostResponse
import com.techjd.devconnector.data.models.UserPosts.Posts
import com.techjd.devconnector.data.models.chat.conversations.Conversations
import com.techjd.devconnector.data.models.chat.messages.Messages
import com.techjd.devconnector.data.models.chat.messages.UserId
import com.techjd.devconnector.data.models.chat.online.MakeOnlineBody
import com.techjd.devconnector.data.models.chat.online.Msg
import com.techjd.devconnector.data.models.chat.onlineusers.OnlineUsers
import retrofit2.Response
import retrofit2.http.*

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
        @Header("x-auth-token") token: String,
        @Body newPost: NewPost
    ): Response<PostResponse>

    @GET("profile/me")
    suspend fun getMyInfo(
        @Header("x-auth-token") token: String
    ): Response<UserInfo>

    @POST("chat/makeMeOnline")
    suspend fun makeMeOnline(
        @Header("x-auth-token") token: String,
        @Body makeOnlineBody: MakeOnlineBody
    ): Response<Msg>

    @DELETE("chat/removeMeOnline")
    suspend fun removeMeOnline(
        @Header("x-auth-token") token: String
    ): Response<Msg>

    @GET("chat/getAllConversations")
    suspend fun getAllConversation(
        @Header("x-auth-token") token: String
    ): Response<Conversations>

    @GET("chat/getOnlineUser")
    suspend fun getAllOnlineUser(
        @Header("x-auth-token") token: String
    ): Response<OnlineUsers>

    @GET("chat/getAllMessages")
    suspend fun getAllMessages(
        @Header("x-auth-token") token: String,
        @Body userId: UserId
    ): Response<Messages>

}