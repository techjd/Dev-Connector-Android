package com.techjd.devconnector.data.models.jobs.alljobs

data class AllJobsItem(
    val __v: Int,
    val _id: String,
    val budget: String,
    val date: String,
    val lookingFor: String,
    val requiredSkills: String,
    val shortDescription: String,
    val user: String,
    val userObj: List<UserObj>
)