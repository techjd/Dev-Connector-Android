package com.techjd.devconnector.respository

import com.techjd.devconnector.api.DevConnectorService
import com.techjd.devconnector.data.models.UserPosts.Posts
import retrofit2.Response

object PostsRepository {

    suspend fun getPost(token: String): Response<Posts> =
        DevConnectorService.devConnectorInstance.getPosts(token)
}