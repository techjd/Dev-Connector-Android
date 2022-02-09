package com.techjd.devconnector.data.models.chat.messages

import com.google.gson.annotations.SerializedName

data class UserId(
    @SerializedName("to")
    val to: String
)
