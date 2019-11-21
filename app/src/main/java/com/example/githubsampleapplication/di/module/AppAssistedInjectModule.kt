package com.example.githubsampleapplication.di.module

import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module

@Module(includes = [AssistedInject_AppAssistedInjectModule::class])
@AssistedModule
abstract class AppAssistedInjectModule {}