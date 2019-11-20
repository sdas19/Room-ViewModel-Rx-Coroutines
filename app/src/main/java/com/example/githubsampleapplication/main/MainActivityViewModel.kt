package com.example.githubsampleapplication.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubsampleapplication.ApiClient
import com.example.githubsampleapplication.RepoDao
import com.example.githubsampleapplication.model.RepositoryResponseModel
import com.example.githubsampleapplication.addToDisposable
import com.example.githubsampleapplication.removeAllDisposables
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.w3c.dom.ls.LSException
import javax.inject.Inject

class MainActivityViewModel
    @Inject constructor(val apiClient: ApiClient, val repoDao: RepoDao): ViewModel() {

    private val TAG = MainActivityViewModel::class.java.simpleName

    internal fun makeRepoApiCall(){
        addToDisposable(
            apiClient.getRepositoryResponse().subscribeOn(Schedulers.io())
                .flatMap { repoList ->
                    repoDao.deleteAll()
                    repoDao.insertAll(repoList)
                    Single.just(repoList)}
                .subscribe(
                    {list -> Log.i(TAG,list.toString())},
                    { error -> Log.i(TAG,error?.message)}
                )
        )
    }

    internal fun getRepoFromDb() : LiveData<List<RepositoryResponseModel>>{
        return repoDao.getAllRepos()
    }

    override fun onCleared() {
        super.onCleared()
        removeAllDisposables()
    }
}