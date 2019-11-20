package com.example.githubsampleapplication

import com.example.githubsampleapplication.model.RepositoryResponseModel

interface RepoClickListener {
    fun onRepoClick(repo : RepositoryResponseModel)
}