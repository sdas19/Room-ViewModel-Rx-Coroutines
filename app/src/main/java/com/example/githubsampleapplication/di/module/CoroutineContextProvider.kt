package com.example.githubsampleapplication.di.module

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/*
* providing proper Dispatcher and we will use it to change / mock dispatchers in unit tests.
* */

open class CoroutineContextProvider @Inject constructor() {

    open val Main: CoroutineContext by lazy { Dispatchers.Main }
    open val IO: CoroutineContext by lazy { Dispatchers.IO }

}