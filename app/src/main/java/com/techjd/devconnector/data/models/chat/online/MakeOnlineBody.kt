package com.techjd.devconnector.data.models.chat.online

import com.google.gson.annotations.SerializedName

data class MakeOnlineBody(
    @SerializedName("socketId")
    val socketId: String
)
