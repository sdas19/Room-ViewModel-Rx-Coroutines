package com.example.githubsampleapplication.di.module

import com.example.githubsampleapplication.DBCleanupWorker
import com.example.githubsampleapplication.Factory.ChildWorkerFactory
import com.example.githubsampleapplication.di.annotation.WorkerKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(DBCleanupWorker::class)
    fun bindBisonWorker(factory: DBCleanupWorker.Factory): ChildWorkerFactory
}