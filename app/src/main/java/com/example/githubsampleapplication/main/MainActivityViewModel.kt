package com.example.githubsampleapplication.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsampleapplication.*
import com.example.githubsampleapplication.model.RepositoryResponseModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import javax.inject.Inject

class MainActivityViewModel
@Inject constructor(val apiClient: ApiClient, val repoDao: RepoDao) : ViewModel() {

    private val TAG = MainActivityViewModel::class.java.simpleName
    internal var errorOccured = MutableLiveData<Boolean>().default(false)

    //CoroutineContext: one way of creating viewmodel scope by using a new job
    // and use its context and then in oncleared cancel the job to avoid leaking
    /*private val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)*/

    internal fun makeRepoApiCall() {
        viewModelScope.launch(Dispatchers.IO) {
            apiClient.getRepositoryResponse().let { repoList ->
                repoDao.deleteAll()
                repoDao.insertAll(repoList)
                if (!repoList.isNullOrEmpty()) {
                    changeErrorState(false)
                    Log.i(TAG, repoList.toString())
                } else {
                    changeErrorState((true))
                }
            }
        }
    }

    fun changeErrorState(isError: Boolean) {
        if (isError)
            errorOccured.postValue(true)
        else
            errorOccured.postValue(false)
    }

    internal fun getRepoFromDb(): LiveData<List<RepositoryResponseModel>> {
        return repoDao.getAllRepos()
    }

    override fun onCleared() {
        super.onCleared()
        removeAllDisposables()
        //completableJob.cancel()
    }
}