package com.techjd.devconnector.respository

import com.techjd.devconnector.api.DevConnectorService

object UserInfoRepository {
    suspend fun getUserInfo(token: String) =
        DevConnectorService.devConnectorInstance.getMyInfo(token)
}