package com.example.githubsampleapplication

import com.example.githubsampleapplication.di.module.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class TestCoroutineContextProvider : CoroutineContextProvider() {
    override val Main: CoroutineContext
        get() = Dispatchers.Unconfined

    override val IO: CoroutineContext
        get() = Dispatchers.Unconfined
}