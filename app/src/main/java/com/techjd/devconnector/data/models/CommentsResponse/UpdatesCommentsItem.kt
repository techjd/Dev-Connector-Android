package com.techjd.devconnector.data.models.CommentsResponse

data class UpdatesCommentsItem(
    val _id: String,
    val avatar: String,
    val date: String,
    val name: String,
    val text: String,
    val user: String
)