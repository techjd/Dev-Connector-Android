package com.techjd.devconnector.data.models.UserInfo

data class Experience(
    val _id: String,
    val company: String,
    val current: Boolean,
    val description: String,
    val from: String,
    val location: String,
    val title: String,
    val to: Any
)