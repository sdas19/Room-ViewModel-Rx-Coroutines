package com.example.githubsampleapplication

import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.githubsampleapplication.Factory.AppWorkerFactory
import com.example.githubsampleapplication.di.component.AppComponent
import com.example.githubsampleapplication.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class GithubSampleApplication : DaggerApplication() {

    lateinit var appComponent: AppComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent.builder().application(this).baseUrl(Constants.baseUrl).build()
        val factory: AppWorkerFactory = appComponent.factory()
        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(factory).build())
        return appComponent
    }

}