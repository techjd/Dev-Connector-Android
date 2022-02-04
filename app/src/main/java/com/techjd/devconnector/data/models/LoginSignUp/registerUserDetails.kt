package com.techjd.devconnector.data.models.LoginSignUp

import com.google.gson.annotations.SerializedName

data class registerUserDetails(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)
