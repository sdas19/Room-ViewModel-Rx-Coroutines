package com.example.githubsampleapplication.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubsampleapplication.Factory.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

@Module
class ViewModelFactoryModule {

    @Provides
    @Singleton
    fun viewModelFactory(providerMap: Map<Class<out ViewModel>, Provider<ViewModel>>): ViewModelProvider.Factory {
        return ViewModelFactory(providerMap)
    }
}
