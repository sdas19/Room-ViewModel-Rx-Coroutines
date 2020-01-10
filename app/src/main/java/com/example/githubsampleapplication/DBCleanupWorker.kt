package com.example.githubsampleapplication

import android.content.Context
import android.util.Log
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.example.githubsampleapplication.Factory.ChildWorkerFactory
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Single

class DBCleanupWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val params: WorkerParameters,
    private val repositoryDao: RepositoryDao
) : RxWorker(appContext, params) {

    override fun createWork(): Single<Result> {
        Log.i(DBCleanupWorker::class.java.simpleName, repositoryDao.hashCode().toString())
        return Single.fromCallable { repositoryDao.deleteAll() }
            .map { Result.success() }
            .onErrorReturn { Result.retry() }
    }

    @AssistedInject.Factory
    interface Factory : ChildWorkerFactory

}