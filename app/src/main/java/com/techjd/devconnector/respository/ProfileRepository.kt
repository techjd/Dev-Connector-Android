package com.techjd.devconnector.respository

import com.techjd.devconnector.api.DevConnectorService
import com.techjd.devconnector.data.models.profiles.Profile
import com.techjd.devconnector.data.models.profiles.ProfileBody
import com.techjd.devconnector.data.models.profiles.ProfileItem
import com.techjd.devconnector.data.models.profiles.profileResponse.CreatedProfileResponse
import retrofit2.Response

object ProfileRepository {

    suspend fun getAllProfiles() : Response<Profile> =
        DevConnectorService.devConnectorInstance.getAllProfiles()

    suspend fun createProfile(token: String, profileBody: ProfileBody): Response<CreatedProfileResponse> =
        DevConnectorService.devConnectorInstance.createProfile(token, profileBody)
}