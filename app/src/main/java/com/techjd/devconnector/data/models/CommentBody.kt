package com.techjd.devconnector.data.models

import com.google.gson.annotations.SerializedName

data class CommentBody(
    @SerializedName("text")
    val text: String
)
