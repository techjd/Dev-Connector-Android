package com.techjd.devconnector.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DevConnectorService {

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://172.20.10.2:5000/api/")
        .build()

    val devConnectorInstance: devConnectorAPI =
        retrofit.create(devConnectorAPI::class.java)
}