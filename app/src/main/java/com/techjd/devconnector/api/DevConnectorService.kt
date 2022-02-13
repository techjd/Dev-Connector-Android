package com.techjd.devconnector.api

import com.techjd.devconnector.Utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DevConnectorService {

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.SERVER_URL)
        .build()

    val devConnectorInstance: devConnectorAPI =
        retrofit.create(devConnectorAPI::class.java)
}