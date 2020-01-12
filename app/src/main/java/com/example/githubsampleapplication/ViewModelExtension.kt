package com.example.githubsampleapplication

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import retrofit2.Response
import java.io.IOException

fun ViewModel.addToDisposable(d: Disposable) {
    DisposableManager.add(d)
}

fun ViewModel.removeAllDisposables() {
    DisposableManager.dispose()
}

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

suspend fun <T : Any> ViewModel.safeApiCall(
    call: suspend () -> Response<T>,
    errorMessage: String
): Result<T>? {
    val result: Result<T> = safeApiResult(call, errorMessage)
    return result
}

private suspend fun <T : Any> safeApiResult(
    call: suspend () -> Response<T>,
    errorMessage: String
): Result<T> {
    val response = call.invoke()
    if (response.isSuccessful) return Result.Success(response.body()!!)
    return Result.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
}
