package com.techjd.devconnector.data.models.chat.newmessage

data class NewMessage(
    val __v: Int,
    val _id: String,
    val body: String,
    val conversation: String,
    val createdAt: String,
    val from: String,
    val to: String,
    val updatedAt: String
)