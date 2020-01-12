package com.example.githubsampleapplication

import com.example.githubsampleapplication.model.RepositoryResponseModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface ApiClient {

    @GET(Constants.repositoryUrlRouter)
    suspend fun getRepositoryResponse(): Response<List<RepositoryResponseModel>>
}