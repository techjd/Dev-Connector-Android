package com.techjd.devconnector.data.models.chat.onlineusers

data class OnlineUsersItem(
    val __v: Int,
    val _id: String,
    val date: String,
    val socketId: String,
    val user: String,
    val userObj: List<UserObj>
)