package com.techjd.devconnector.data.models.profiles

import com.google.gson.annotations.SerializedName

data class ProfileBody(
    @SerializedName("status")
    val status: String,
    @SerializedName("company")
    val company: String,
    @SerializedName("skills")
    val skills: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("bio")
    val bio: String
)
