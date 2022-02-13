package com.techjd.devconnector.respository

import com.techjd.devconnector.api.DevConnectorService
import com.techjd.devconnector.data.models.chat.online.Msg
import com.techjd.devconnector.data.models.jobs.JobBody
import com.techjd.devconnector.data.models.jobs.JobPostedResponse
import com.techjd.devconnector.data.models.jobs.alljobs.AllJobs
import com.techjd.devconnector.data.models.jobs.singlejob.SingleJob
import retrofit2.Response

object JobsRepository {

    suspend fun addANewJob(token: String, jobBody: JobBody): Response<JobPostedResponse> =
        DevConnectorService.devConnectorInstance.addANewJob(token, jobBody)

    suspend fun getAllJobs(token: String): Response<AllJobs> =
        DevConnectorService.devConnectorInstance.getAllJobs(token)

    suspend fun getSingleJob(token: String, id: String): Response<SingleJob> =
        DevConnectorService.devConnectorInstance.getASingleJob(token, id)

    suspend fun updateASingleJob(token: String, id: String, jobBody: JobBody): Response<Msg> =
        DevConnectorService.devConnectorInstance.updateASingleJob(token, id, jobBody)
}