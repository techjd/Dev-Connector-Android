package com.techjd.devconnector.data.models.UserInfo

data class UserInfo(
    val __v: Int,
    val _id: String,
    val bio: String,
    val company: String,
    val date: String,
    val education: List<Education>,
    val experience: List<Experience>,
    val githubusername: String,
    val location: String,
    val skills: List<String>,
    val social: Social,
    val status: String,
    val user: User,
    val website: String
)