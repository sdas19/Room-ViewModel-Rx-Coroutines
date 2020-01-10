package com.example.githubsampleapplication.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubsampleapplication.*
import com.example.githubsampleapplication.model.RepositoryResponseModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivityViewModel
@Inject constructor(val apiClient: ApiClient, val repositoryDao: RepositoryDao) : ViewModel() {

    private val TAG = MainActivityViewModel::class.java.simpleName
    internal var errorOccured = MutableLiveData<Boolean>().default(false)

    internal fun makeRepoApiCall() {
        addToDisposable(
            apiClient.getRepositoryResponse().subscribeOn(Schedulers.io())
                .flatMap { repoList ->
                    repositoryDao.deleteAll()
                    repositoryDao.insertAll(repoList)
                    Single.just(repoList)
                }
                .subscribe(
                    { list ->
                        if(!list.isNullOrEmpty()) {
                            changeErrorState(false)
                            Log.i(TAG, list.toString())
                        }
                    },
                    { error ->
                        changeErrorState((true))
                        Log.i(TAG, error?.message)
                    }
                )
        )
    }

    fun changeErrorState(isError : Boolean){
        if(isError)
            errorOccured.postValue(true)
        else
            errorOccured.postValue(false)
    }

    internal fun getRepoFromDb(): LiveData<List<RepositoryResponseModel>> {
        return repositoryDao.getAllRepos()
    }

    override fun onCleared() {
        super.onCleared()
        removeAllDisposables()
    }
}