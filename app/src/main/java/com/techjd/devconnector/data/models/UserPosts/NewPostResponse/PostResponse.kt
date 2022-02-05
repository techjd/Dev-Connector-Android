package com.techjd.devconnector.data.models.UserPosts.NewPostResponse

data class PostResponse(
    val __v: Int,
    val _id: String,
    val avatar: String,
    val comments: List<Any>,
    val date: String,
    val likes: List<Any>,
    val name: String,
    val text: String,
    val user: String
)