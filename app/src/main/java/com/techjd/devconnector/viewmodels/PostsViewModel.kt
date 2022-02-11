package com.techjd.devconnector.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techjd.devconnector.Utils.Resource
import com.techjd.devconnector.data.models.CommentBody
import com.techjd.devconnector.data.models.CommentsResponse.UpdatesComments
import com.techjd.devconnector.data.models.UserPosts.NewPostResponse.NewPost
import com.techjd.devconnector.data.models.UserPosts.NewPostResponse.PostResponse
import com.techjd.devconnector.data.models.UserPosts.Posts
import com.techjd.devconnector.data.models.UserPosts.SinglePost.SinglePost
import com.techjd.devconnector.respository.PostsRepository
import kotlinx.coroutines.launch

class PostsViewModel : ViewModel() {
    private val _posts: MutableLiveData<Resource<Posts>> = MutableLiveData()
    val postLiveData: LiveData<Resource<Posts>> = _posts
    private val _postsResponse: MutableLiveData<Resource<PostResponse>> = MutableLiveData()
    val postsResponseLiveData: LiveData<Resource<PostResponse>> = _postsResponse
    private val _singlePost: MutableLiveData<Resource<SinglePost>> = MutableLiveData()
    val singlePostLiveData: LiveData<Resource<SinglePost>> = _singlePost
    private val _comment: MutableLiveData<Resource<UpdatesComments>> = MutableLiveData()
    val comment: LiveData<Resource<UpdatesComments>> = _comment

    suspend fun getPosts(token: String) {
        Log.d("PostsViewModel", "getPosts: Called")
        viewModelScope.launch {
            _posts.value = Resource.Loading(message = "Data is Loading")
            val response = PostsRepository.getPost(token)
            if (response.isSuccessful) {
                _posts.value = Resource.Success(data = response.body()!!)
            } else {
                Log.d("ERROR", "getPosts: ${response.message()}")
                _posts.value = Resource.Error(message = "Some Error Occurred")
            }
        }
    }

    suspend fun updatePosts(token: String, newPost: NewPost) {
        viewModelScope.launch {
            _postsResponse.value = Resource.Loading(message = "Post is Uploading")
            val response = PostsRepository.newPost(token, newPost)
            if (response.isSuccessful) {
                _postsResponse.value = Resource.Success(data = response.body()!!)
            } else {
                _postsResponse.value = Resource.Error(message = "Some Error Occurred")
            }
        }
    }

    suspend fun getSinglePost(token: String, postId: String) {
        viewModelScope.launch {
            _singlePost.value = Resource.Loading(message = "Post is Uploading")
            val response = PostsRepository.getSinglePost(token, postId)
            if (response.isSuccessful) {
                _singlePost.value = Resource.Success(data = response.body()!!)
            } else {
                _singlePost.value = Resource.Error(message = "Some Error Occurred")
            }
        }
    }

    suspend fun postAComment(token: String, id: String, commentBody: CommentBody) {
        viewModelScope.launch {
            _comment.value = Resource.Loading(message = "Comment is Loading")
            val response = PostsRepository.getAllComments(token, id, commentBody)
            if (response.isSuccessful) {
                _comment.value = Resource.Success(data = response.body()!!)
            } else {
                _comment.value = Resource.Error(message = "Some Error Occurred")
            }
        }
    }
}