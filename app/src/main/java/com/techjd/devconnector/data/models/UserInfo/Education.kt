package com.techjd.devconnector.data.models.UserInfo

data class Education(
    val _id: String,
    val current: Boolean,
    val degree: String,
    val description: String,
    val fieldofstudy: String,
    val from: String,
    val school: String,
    val to: String
)