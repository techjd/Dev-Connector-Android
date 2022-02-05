package com.techjd.devconnector.data.models.UserPosts.NewPostResponse

import com.google.gson.annotations.SerializedName

data class NewPost(
    @SerializedName("text")
    val text: String
)
