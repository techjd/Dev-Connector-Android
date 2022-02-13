package com.techjd.devconnector.data.models.profiles.profileResponse

data class CreatedProfileResponse(
    val __v: Int,
    val _id: String,
    val bio: String,
    val company: String,
    val date: String,
    val education: List<Any>,
    val experience: List<Any>,
    val location: String,
    val skills: List<String>,
    val status: String,
    val user: String
)