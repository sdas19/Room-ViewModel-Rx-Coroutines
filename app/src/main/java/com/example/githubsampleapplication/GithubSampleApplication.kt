package com.example.githubsampleapplication

import com.example.githubsampleapplication.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class GithubSampleApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).baseUrl(Constants.baseUrl).build()
    }
}