package com.techjd.devconnector.respository

import com.techjd.devconnector.api.DevConnectorService
import com.techjd.devconnector.data.models.CommentBody
import com.techjd.devconnector.data.models.UserPosts.NewPostResponse.NewPost
import com.techjd.devconnector.data.models.UserPosts.Posts
import retrofit2.Response

object PostsRepository {

    suspend fun getPost(token: String): Response<Posts> =
        DevConnectorService.devConnectorInstance.getPosts(token)

    suspend fun newPost(token: String, newPost: NewPost) =
        DevConnectorService.devConnectorInstance.updatePosts(token, newPost)

    suspend fun getSinglePost(token: String, postId: String) =
        DevConnectorService.devConnectorInstance.getSinglePost(token, postId)

    suspend fun getAllComments(token: String, id: String, commentBody: CommentBody) =
        DevConnectorService.devConnectorInstance.postAComment(token, id, commentBody)
}