package com.example.githubsampleapplication.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubsampleapplication.ApiClient
import com.example.githubsampleapplication.model.RepositoryResponseModel
import com.example.githubsampleapplication.addToDisposable
import com.example.githubsampleapplication.removeAllDisposables
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivityViewModel
    @Inject constructor(context : Context, apiClient: ApiClient): ViewModel() {

    private val TAG = MainActivityViewModel::class.java.simpleName
    internal val dataResponse : MutableLiveData<List<RepositoryResponseModel>> = MutableLiveData()

    init {
        addToDisposable(
            apiClient.getRepositoryResponse().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {response -> Log.i(TAG,response.toString())
                        dataResponse.value = response},
                    {error -> Log.i(TAG,error.toString())}
                )
        )

    }

    override fun onCleared() {
        super.onCleared()
        removeAllDisposables()
    }
}