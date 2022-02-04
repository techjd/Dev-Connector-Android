package com.techjd.devconnector.data.models.UserPosts

data class PostsItem(
    val __v: Int,
    val _id: String,
    val avatar: String,
    val comments: List<Comment>,
    val date: String,
    val likes: List<Like>,
    val name: String,
    val text: String,
    val user: String
)