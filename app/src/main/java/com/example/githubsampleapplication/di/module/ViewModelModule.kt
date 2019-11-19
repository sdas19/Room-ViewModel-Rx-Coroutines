package com.example.githubsampleapplication.di.module

import androidx.lifecycle.ViewModel
import com.example.githubsampleapplication.main.MainActivityViewModel
import com.example.githubsampleapplication.di.annotation.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainViewModel(mainActivityViewModel: MainActivityViewModel) : ViewModel
}
