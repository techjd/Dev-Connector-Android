package com.techjd.devconnector.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techjd.devconnector.Utils.Resource
import com.techjd.devconnector.data.models.chat.online.Msg
import com.techjd.devconnector.data.models.jobs.JobBody
import com.techjd.devconnector.data.models.jobs.JobPostedResponse
import com.techjd.devconnector.data.models.jobs.alljobs.AllJobs
import com.techjd.devconnector.data.models.jobs.singlejob.SingleJob
import com.techjd.devconnector.respository.JobsRepository
import kotlinx.coroutines.launch

class JobsViewModel : ViewModel() {
    private val _postJob: MutableLiveData<Resource<JobPostedResponse>> = MutableLiveData()
    val postJob: LiveData<Resource<JobPostedResponse>> = _postJob
    private val _allJobs: MutableLiveData<Resource<AllJobs>> = MutableLiveData()
    val allJobs: LiveData<Resource<AllJobs>> = _allJobs
    private val _singleJob: MutableLiveData<Resource<SingleJob>> = MutableLiveData()
    val singleJob: LiveData<Resource<SingleJob>> = _singleJob
    private val _updateJob: MutableLiveData<Resource<Msg>> = MutableLiveData()
    val updateJob: LiveData<Resource<Msg>> = _updateJob

    suspend fun postJob(token: String, jobBody: JobBody) {
        viewModelScope.launch {
            _postJob.value = Resource.Loading("Job is Being Post")
            val response = JobsRepository.addANewJob(token, jobBody)
            if (response.isSuccessful) {
                _postJob.value = Resource.Success(response.body()!!)
            } else {
                _postJob.value = Resource.Error("Some Error Occurred")
            }
        }
    }

    suspend fun getAllJobs(token: String) {
        viewModelScope.launch {
            _allJobs.value = Resource.Loading("All Jobs are being Loaded")
            val response = JobsRepository.getAllJobs(token)
            if (response.isSuccessful) {
                _allJobs.value = Resource.Success(response.body()!!)
            } else {
                _allJobs.value = Resource.Error("Some Error Occurred")
            }
        }
    }

    suspend fun getASingleJob(token: String, id: String) {
        viewModelScope.launch {
            _singleJob.value = Resource.Loading("Getting A Single Job")
            val response = JobsRepository.getSingleJob(token, id)
            if (response.isSuccessful) {
                _singleJob.value = Resource.Success(response.body()!!)
            } else {
                _singleJob.value = Resource.Error("Some Error Occurred")
            }
        }
    }

    suspend fun updateASinglePost(token: String, id: String, jobBody: JobBody) {
        viewModelScope.launch {
            _updateJob.value = Resource.Loading("Updating Single Job")
            val response = JobsRepository.updateASingleJob(token, id, jobBody)
            if (response.isSuccessful) {
                _updateJob.value = Resource.Success(response.body()!!)
            } else {
                _updateJob.value = Resource.Error("Some Error Occurred")
            }
        }
    }
}