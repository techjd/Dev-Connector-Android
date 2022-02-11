package com.techjd.devconnector.data.models.chat.sendMessages

import com.google.gson.annotations.SerializedName

data class SendMessageBody(
    @SerializedName("to")
    val to: String,
    @SerializedName("message")
    val message: String
)
