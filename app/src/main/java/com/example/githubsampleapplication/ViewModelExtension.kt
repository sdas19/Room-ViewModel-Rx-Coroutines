package com.example.githubsampleapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubsampleapplication.DisposableManager
import io.reactivex.disposables.Disposable

fun ViewModel.addToDisposable(d : Disposable){
    DisposableManager.add(d)
}

fun ViewModel.removeAllDisposables(){
    DisposableManager.dispose()
}

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }
