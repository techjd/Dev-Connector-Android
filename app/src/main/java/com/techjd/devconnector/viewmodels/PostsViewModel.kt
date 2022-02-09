package com.techjd.devconnector.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techjd.devconnector.Utils.Resource
import com.techjd.devconnector.data.models.UserPosts.NewPostResponse.NewPost
import com.techjd.devconnector.data.models.UserPosts.NewPostResponse.PostResponse
import com.techjd.devconnector.data.models.UserPosts.Posts
import com.techjd.devconnector.respository.PostsRepository
import kotlinx.coroutines.launch

class PostsViewModel : ViewModel() {
    private val _posts: MutableLiveData<Resource<Posts>> = MutableLiveData()
    val postLiveData: LiveData<Resource<Posts>> = _posts
    private val _postsResponse: MutableLiveData<Resource<PostResponse>> = MutableLiveData()
    val postsResponseLiveData: LiveData<Resource<PostResponse>> = _postsResponse

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
}