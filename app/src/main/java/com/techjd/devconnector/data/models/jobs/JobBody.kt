package com.techjd.devconnector.data.models.jobs

import com.google.gson.annotations.SerializedName

data class JobBody(
    @SerializedName("lookingFor")
    val lookingFor: String,
    @SerializedName("shortDescription")
    val shortDescription: String,
    @SerializedName("requiredSkills")
    val requiredSkills: String,
    @SerializedName("budget")
    val budget: String

)
