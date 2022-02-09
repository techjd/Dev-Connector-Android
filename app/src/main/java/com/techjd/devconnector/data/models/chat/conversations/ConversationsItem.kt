package com.techjd.devconnector.data.models.chat.conversations

data class ConversationsItem(
    val __v: Int,
    val _id: String,
    val date: String,
    val lastMessage: String,
    val recipientObj: List<RecipientObj>,
    val recipients: List<String>
)