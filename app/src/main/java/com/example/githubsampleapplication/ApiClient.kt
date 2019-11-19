package com.example.githubsampleapplication

import com.example.githubsampleapplication.Model.RepositoryResponseModel
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiClient {

    @GET(Constants.repositoryUrlRouter)
    fun getRepositoryResponse() : Single<List<RepositoryResponseModel>>
}