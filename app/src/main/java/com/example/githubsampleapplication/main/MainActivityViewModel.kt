package com.example.githubsampleapplication.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubsampleapplication.*
import com.example.githubsampleapplication.model.RepositoryResponseModel
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.w3c.dom.ls.LSException
import javax.inject.Inject

class MainActivityViewModel
    @Inject constructor(val apiClient: ApiClient, val repoDao: RepoDao): ViewModel() {

    private val TAG = MainActivityViewModel::class.java.simpleName
    internal var errorOccured = MutableLiveData<Boolean>().default(false)

    internal fun makeRepoApiCall(){
        addToDisposable(
            apiClient.getRepositoryResponse().subscribeOn(Schedulers.io())
                .flatMap { repoList ->
                    repoDao.deleteAll()
                    repoDao.insertAll(repoList)
                    Single.just(repoList)}
                .subscribe(
                    {list ->
                        errorOccured.postValue(false)
                        Log.i(TAG,list.toString())},
                    { error ->
                        errorOccured.postValue(true)
                        Log.i(TAG,error?.message)}
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